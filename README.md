# akka-http-docker

Sample application using Akka Http REST API to test docker image generation with plugin `sbt-native-packager`.

To use the endpoint from the terminal:

```
curl http://localhost:8080/hello
```

##Setup for create docker images automatically

First of all you need Docker installed and running in your system.

Add native packager in `properties/plugins.sbt`:

```
addSbtPlugin("com.typesafe.sbt" % "sbt-native-packager" % "1.7.6")
```

Reload the project.

In `build.sbt` add the following:

```
mainClass in Compile := Some("com.gm.AkkaHttpService")
enablePlugins(AshScriptPlugin)
```

`enablePlugins(AshScriptPlugin)` must be at the bottom of `build.sbt`.

To build the image run:

`sbt docker:publishLocal`

After a couple of minutes you will see the `[success]` message (I saw some `[error]` messages in red which seems be fine anyway ... a little weird ...)

From the command lined run:

```
docker images
```

You will see the image generated with name `akka-http-docker` and tag `0.1`:

```
REPOSITORY                                      TAG                IMAGE ID       CREATED          SIZE
akka-http-docker                                0.1                94866ee80cd1   27 seconds ago   539MB
cassandra                                       3                  b52b5f0d6e11   13 days ago      402MB
cassandra                                       latest             b52b5f0d6e11   13 days ago      402MB
...
```

So now we can run the image using docker:

```
 docker run --rm -p8080:8080 akka-http-docker:0.1
```

And then we could check using `curl` that the service is running ok.

We can choose what image use with the following line in `build.sbt`:

```
dockerBaseImage := "adoptopenjdk/openjdk11:alpine-slim"
```

Other images you can use:

```
dockerBaseImage := "openjdk:jre"
dockerBaseImage := “openjdk:jre-alpine”
```

To remove the image when you are not using it:

```
docker rmi akka-http-docker:0.1
```

Can we change the name of the image? Yes:

```
Docker / packageName := "akka-http-dockerized"
```

Can we change the tag? Yes:

```
dockerAliases ++= Seq(dockerAlias.value.withTag(Option("3.1")))
```