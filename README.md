# Spring Cloud Sleuth GRPC

This project demonstrates how Spring Cloud Sleuth can be used to instrument gRPC calls.

The auto-configuration for instrumenting gRPC requires the following two libraries to be on the classpath:

- grpc-spring-boot-starter
- brave-instrumentation-grpc

## Running without Zipkin:

1. Edit src/main/resources/application.yml and uncomment the "sample.zipkin.enable=false"
2. Import this maven project into your favorite IDE
3. Run the spring boot application.
4. In a browser "http://localhost:8080/hi/fred"


## Running with Zipkin:

1. For convenience, there is a docker-compose file in the project that can be used to launch zipkin locally. Otherwise, you will need to run zipkin separately.
2. Edit "src/main/resources/application.yml" and comment out the "sample.zipkin.enable=false"
3. Import this maven project into your favorite IDE (note, I have not setup m2e to automatically build the stubs from within Eclipse)
4. Run the spring boot application.
5. In a browser "http://localhost:8080/hi/fred"
6. Check out the results in zipkin, if running locally http://localhost:9411


## Notes:

- This project is using a snapshot version of spring-cloud-sleuth-core that includes the gRPC auto-configuration. Once this has been included in an official release, this override can be removed. 
- This sample hosts both the gRPC client and server, in a real environment these would be separate.
- This sample uses a maven plugin to generate GRPC stubs/skeletons.
- You may see initial compiler errors when importing into Eclipse. This can be corrected by highlighting the project and selecting "Maven->Update Project" which
  will trigger the stubs/skeletons to be correctly generated. 
