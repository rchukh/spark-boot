# Use Mesos image as initial layer
FROM mesosphere/mesos:1.0.1-2.0.93.ubuntu1404
MAINTAINER roman.chukh@gmail.com
VOLUME /tmp

# This is needed for Oracle Java license acceptance
# TODO: Switch to OpenJDK
ENV DEBIAN_FRONTEND noninteractive

# Install Oracle JDK instead of OpenJDK
RUN apt-get remove -y --auto-remove openjdk* && \
    apt-get update && \
    apt-get install -y software-properties-common python-software-properties debconf-utils && \
    add-apt-repository -y ppa:webupd8team/java && \
    apt-get update && \
    echo "oracle-java8-installer shared/accepted-oracle-license-v1-1 select true" | debconf-set-selections && \
    echo "oracle-java8-installer shared/accepted-oracle-license-v1-1 seen true" | debconf-set-selections && \
    apt-get -y install oracle-java8-installer

# NOTE: It is recommended to avoid using ROOT inside of container.
# NOTE: Adding additional user and using operation like 'chown' will create additonal layer for affected files, thus increasing image size.
# TODO: Uncomment and test
#ENV USER_NAME sparkboot
#ENV APP_HOME /home/$USER_NAME/app
ENV APP_HOME /opt/app

# The path is inherited for mesos layer, but we still need to define the environment variable for Spark
ENV MESOS_NATIVE_JAVA_LIBRARY /usr/local/lib/libmesos.so

#RUN adduser --disabled-password --gecos "" $USER_NAME && mkdir $APP_HOME
RUN mkdir $APP_HOME

ADD api-*.jar $APP_HOME/api.jar

#RUN chown -R $USER_NAME $APP_HOME

#USER $USER_NAME
WORKDIR $APP_HOME

#You can use a RUN command to "touch" the jar file so that it has a file modification time (Docker creates all container files in an "unmodified" state by default).
#This actually isn’t important for the simple app that we wrote, but any static content (e.g. "index.html") would require the file to have a modification time.
#RUN sh -c 'touch api.jar'

# Expose Spring Boot port
EXPOSE 9090

# Set some sane minimal Java Heap max usage.
#
# NOTE: This can be overriden by docker env settings
# NOTE: This must be set according to the memory that is allocated to the docker container itself.
#       (If not - the container will constantly fail without some sane logs)
# SEE: http://matthewkwilliams.com/index.php/2016/03/17/docker-cgroups-memory-constraints-and-java-cautionary-tale/
ENV JAVA_OPTS -Xmx512m
ENTRYPOINT exec java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar api.jar