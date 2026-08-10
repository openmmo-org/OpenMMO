# Needs `./gradlew :server.game:installDist` to have run first.
FROM eclipse-temurin:25-jre-noble

RUN groupadd --gid 10001 openmmo \
    && useradd --no-log-init --uid 10001 --gid openmmo --home-dir /opt/openmmo \
       --shell /usr/sbin/nologin openmmo

WORKDIR /opt/openmmo
COPY server.game/build/install/server.game/lib/ lib/
COPY --chmod=755 server.game/build/install/server.game/bin/ bin/

ENV JAVA_TOOL_OPTIONS="-XX:MaxRAMPercentage=75"

USER 10001:10001
EXPOSE 7777
ENTRYPOINT ["/opt/openmmo/bin/server.game"]
