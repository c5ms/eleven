package com.eleven.gateway.admin.support;

import com.cnetong.common.cluster.MetadataManager;
import com.eleven.gateway.admin.constants.GateAdminConstants;
import com.eleven.gateway.admin.domain.repository.GateResourceRepository;
import com.eleven.gateway.core.GatewayContent;
import com.eleven.gateway.core.GatewayLoggers;
import com.eleven.gateway.core.GatewayResource;
import com.eleven.gateway.core.utils.CompressUtils;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.compress.archivers.ArchiveEntry;
import org.apache.commons.compress.archivers.ArchiveInputStream;
import org.apache.commons.compress.archivers.tar.TarArchiveInputStream;
import org.apache.commons.compress.archivers.zip.ZipArchiveInputStream;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.jetbrains.annotations.NotNull;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.CacheControl;
import org.springframework.http.MediaType;
import org.springframework.http.MediaTypeFactory;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;

@Slf4j
@RequiredArgsConstructor
public class DefaultGatewayResource implements GatewayResource {

    private final String id;
    private final String defineId;
    private final String metaDataKey;

    private final GateResourceRepository gateResourceRepository;
    private final MetadataManager metadataManager;
    private final AtomicReference<Map<String, Entry>> contentRef = new AtomicReference<>(new HashMap<>());

    private String index;
    private String resourceName;
    private boolean enabledGzip;
    private CacheControl cacheControl;

    private String[] hits;

    public DefaultGatewayResource(String id,
                                  String defineId,
                                  GateResourceRepository gateResourceRepository,
                                  MetadataManager metadataManager) {
        this.id = id;
        this.defineId = defineId;
        this.metaDataKey = GateAdminConstants.getResourceKey(defineId);
        this.gateResourceRepository = gateResourceRepository;
        this.metadataManager = metadataManager;
        metadataManager.updateGlobalVersion(metaDataKey);
    }

    @NotNull
    private static MediaType getMediaType(String name) {
        return MediaTypeFactory.getMediaType(name).orElse(MediaType.APPLICATION_OCTET_STREAM);
    }

    public void sync() {
        this.metadataManager.syncVersion(metaDataKey, metadata -> this.initialize());
    }

    protected void initialize() {
        Map<String, Entry> contents = new HashMap<>();
        var resOpt = gateResourceRepository.findById(this.defineId);
        if (resOpt.isPresent()) {
            var res = resOpt.get();
            this.resourceName = res.getName();
            this.cacheControl = CacheControl.noCache();
            this.enabledGzip = BooleanUtils.isTrue(res.getEnabledGzip());
            if (null != res.getCacheDays() && res.getCacheDays() > 0) {
                this.cacheControl = CacheControl.maxAge(Duration.ofDays(res.getCacheDays())).cachePublic();
            }
            this.index = res.getIndex();
            this.hits = new String[]{"", res.getIndex()};
            var pack = res.getPack();
            if (null != pack) {
                var content = pack.getContent();
                var body = content.getBody();
                try (InputStream is = body.getBinaryStream()) {
                    ArchiveInputStream archiveInputStream = null;
                    if (pack.getFileName().endsWith("tar") || pack.getFileName().endsWith("tar.gz")) {
                        archiveInputStream = new TarArchiveInputStream(is);
                    }
                    if (pack.getFileName().endsWith("zip")) {
                        archiveInputStream = new ZipArchiveInputStream(is);
                    }
                    contents.putAll(archiveToContents(archiveInputStream));
                } catch (Exception e) {
                    res.error(e);
                    gateResourceRepository.save(res);
                    GatewayLoggers.RUNTIME_LOGGER.error("静态资源加载失败", e);
                }
            }
        }
        contentRef.set(contents);
        GatewayLoggers.RUNTIME_LOGGER.info("静态资源加载,{}, v{}", id, metadataManager.getGlobalVersion(metaDataKey));
    }

    @Override
    public String getName() {
        this.sync();
        return resourceName;
    }

    @Override
    public String getIndex() {
        this.sync();
        return index;
    }

    @Override
    public Set<String> getFiles() {
        this.sync();
        return this.contentRef.get().keySet();
    }

    @Override
    public String getId() {
        this.sync();
        return id;
    }

    @Override
    public Optional<GatewayContent> load(String path) {
        this.sync();
        for (String index : hits) {
            var checkPath = Paths.get("/", path, index);
            var name = checkPath.toString();
            var entry = contentRef.get().get(name);
            if (null != entry) {
                return Optional.of(GatewayContent.builder()
                        .body(new ByteArrayResource(entry.getContent()))
                        .mediaType(entry.getMediaType())
                        .encoding(enabledGzip ? "gzip" : null)
                        .cacheControl(cacheControl)
                        .build());
            }
        }
        return Optional.empty();
    }

    private Map<String, Entry> archiveToContents(ArchiveInputStream archiveInputStream) throws IOException {
        Map<String, Entry> contents = new HashMap<>();
        if (null != archiveInputStream) {
            for (ArchiveEntry nextEntry = archiveInputStream.getNextEntry(); nextEntry != null; nextEntry = archiveInputStream.getNextEntry()) {
                if (!nextEntry.isDirectory()) {
                    var path = Path.of(nextEntry.getName());
                    if (!path.getFileName().toString().startsWith(".")) {
                        MediaType type = getMediaType(path.toString());
                        var bytes = IOUtils.toByteArray(archiveInputStream);
                        if (this.enabledGzip) {
                            bytes = CompressUtils.compress(bytes);
                        }
                        var entryPath = nextEntry.getName();
                        if (entryPath.startsWith("./")) {
                            entryPath = entryPath.substring(1);
                        }
                        if (!entryPath.startsWith("/")) {
                            entryPath = "/" + entryPath;
                        }
                        var entry = new Entry(bytes, type);
                        contents.put(entryPath, entry);
                    }
                }
            }
        }
        return contents;
    }

    @Value
    public static class Entry {

        byte[] content;
        MediaType mediaType;

        public Entry(byte[] content, MediaType mediaType) {
            this.content = content;
            this.mediaType = mediaType;
        }
    }
}
