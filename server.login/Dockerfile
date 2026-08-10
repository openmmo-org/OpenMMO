# Needs `./gradlew :server.login:installDist` to have run first.
FROM eclipse-temurin:25-jre-noble

RUN groupadd --gid 10001 openmmo \
    && useradd --no-log-init --uid 10001 --gid openmmo --home-dir /opt/openmmo \
       --shell /usr/sbin/nologin openmmo

WORKDIR /opt/openmmo
COPY server.login/build/install/server.login/lib/ lib/
COPY --chmod=755 server.login/build/install/server.login/bin/ bin/

ENV JAVA_TOOL_OPTIONS="-XX:MaxRAMPercentage=75"

USER 10001:10001
EXPOSE 2106
ENTRYPOINT ["/opt/openmmo/bin/server.login"]
