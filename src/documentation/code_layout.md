# Package by feature, not layer

http://www.javapractices.com/topic/TopicAction.do?Id=205


There many other aspect other than coupling for package design I would suggest to look at OOAD Priciples, especially package design priciples like

- **REP The Release Reuse Equivalency Principle** The granule of reuse is the granule of release.
- **CCP The Common Closure Principle** Classes that change together are packaged together.
- **CRP The Common Reuse Principle** Classes that are used together are packaged together.
- **ADP The Acyclic Dependencies** Principle The dependency graph of packages must have no cycles.
- **SDP The Stable Dependencies** Principle Depend on the direction of stability.
- **SAP The Stable Abstractions** Principle Abstractness increases with stability.
- for more information you can read book "Agile Software Development, Principles, Patterns, and Practices"
