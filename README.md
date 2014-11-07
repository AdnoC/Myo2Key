Myo2Key
=======
Myo2Key will be like JoyToKey, but for the Myo instead of Xbox controllers. It is being written in Java using NicholasAStuart's [myo-java](http://github.com/NicholasAStuart/myo-java).

##Building
Myo2Key uses [Apache Ant](http://ant.apache.org/) to automate building. Once you have all the dependancies in place, you can build the project with
```
ant build
```
The project can be run with
```
ant run
```
You can do both if you just use
```
ant
```

## Dependancies
Myo2Key requires NicholasAStuart's java bindings. Compile that then place the jar in the "libs" folder.

Also, make sure you have the JNI libraries from NicholasAStuart's repo in your classpath.

 

In the future, those libs will be included and automatically added when compiling.
