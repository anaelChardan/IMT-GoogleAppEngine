# appengine-java-quickstart

## Why?

Lesson at IMT by Julien Landuré from Zenika

## Usage

### Clone 

Clone this repository using

```
git clone https://github.com/jlandure/appengine-java-quickstart
cd appengine-java-quickstart
```

### Launch

Launch the devserver using the Docker image `zenika/alpine-appengine-java`

```
docker run --rm -it -h localhost -v ~/.m2:/root/.m2 -v $(pwd):/usr/src/app -w /usr/src/app -p 8080:8080 zenika/alpine-appengine-java
```

### Check

Open your browser to `http://localhost:8080/hello` and see something like 

```
Hello App Engine - Standard using 1.9.58 Java 1.8
```

## Project from CloudSDK official documentation

The code source is copied on purpose from https://github.com/GoogleCloudPlatform/getting-started-java/

See this [specific folder](https://github.com/GoogleCloudPlatform/getting-started-java/tree/master/appengine-standard-java8/helloworld) to check the source code

## TP

```
docker run --rm -it -h localhost -v ~/.m2:/root/.m2 -v $(pwd):/usr/src/app -v ~/.config/gcloud:/root/.config/gcloud -w /usr/src/app -p 8080:8080 zenika/alpine-appengine-java bash
```

```
gcloud auth login
#copy paste the url in your browser and then paste the token in your bash
gcloud config set project YOUR_PROJECT_ID
```

```
mvn -Dapp.deploy.version=v0 appengine:deploy
```

-> Here you have your app running