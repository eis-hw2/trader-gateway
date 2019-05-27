FROM registry.cn-shenzhen.aliyuncs.com/javajs/java
VOLUME /tmp
RUN mkdir /app
ADD target/trader-0.0.1-SNAPSHOT.jar /app/app.jar
ADD deploy.sh /app
ENV TZ=Asia/Shanghai
RUN sh -c 'touch /app/app.jar' && ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone
WORKDIR /app
RUN chmod a+x deploy.sh
EXPOSE 9090
CMD /app/deploy.sh
