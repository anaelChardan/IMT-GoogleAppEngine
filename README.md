# appengine-java-quickstart

## Launch

Use the Docker image `zenika/alpine-appengine-java` for that

```
docker run -it -v ~/.m2:/root/.m2 -v $(pwd):/usr/src/app -w /usr/src/app -p 8080:8080 zenika/alpine-appengine-java
```

## Check

Open your browser to `http://localhost:8080/hello` and see something like 

```
Hello App Engine - Standard using 1.9.58 Java 1.8
```

## Project from CloudSDK official documentation

The code source is copied on purpose from https://github.com/GoogleCloudPlatform/getting-started-java/

See this [specific folder](https://github.com/GoogleCloudPlatform/getting-started-java/tree/master/appengine-standard-java8/helloworld) to check the source code
