FROM openjdk:17
EXPOSE 9000
COPY / /app/
WORKDIR /app
RUN chmod u+x /app/bin/run
HEALTHCHECK --interval=10s --timeout=3s  --retries=3 CMD curl http://localhost:9000/actuator/health || exit 1
ENTRYPOINT ["/app/bin/run"]
