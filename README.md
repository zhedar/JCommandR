JCommandR
=========

This is an old university project with the goal to create a simple bridge between the Java language and [R](https://www.r-project.org).  
Existing interfaces like [rJava](https://github.com/s-u/rJava) had insufficiences at that time, mostly like requiring DLL dependencies.

The project was designed with extensibility in mind, so that various implementations of the actual conversation between R and Java are possible. We orginally thought of socket or simple CLI communication and implemented the latter in a very simple way, which works but can of no means be considered stable.
