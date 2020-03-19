FROM openjdk:8
MAINTAINER James Lindstorff <jlindstorff@gmail.com>

ENTRYPOINT ["java", "-cp", "/usr/share/demo/demo.jar:/usr/share/demo/lib/*", "dk.eb.sample.grpc.demo.DemoServer"]

# Add Maven dependencies (not shaded into the artifact; Docker-cached)
ADD target/lib           /usr/share/demo/lib
# Add the service itself
ARG JAR_FILE
ADD target/${JAR_FILE} /usr/share/demo/demo.jar
