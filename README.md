A friend taking an introductory Java class asked for an example of polymorphism in Java.
Whipped this up.

**Polymorphism: Whatever floats your boat**

*Real-world analogy*
If you were told to go relax, what would you do? If you're an introvert, you might go find a quite nook and read your favorite book, away from everyone.  If, instead, you're an extrovert, you might head for your favorite club to dance.  Either way, after you're done, you'll be relaxed.  And, generally, the person who told you to go relax doesn't care particularly how you get it accomplished.  After all, relaxation takes many forms.  And 'many forms' is what polymorphism is.

*Real-world analogy in code*

Consider the following classes to model people:
package com.examples.r.us;

public class PolymorphismExample {

  /* A person has a name and can say hi to us */
  /* ignore the word static - it's complicated */ static
  /* Note the word abstract - it's relevant */ abstract
  class Person {
    String name;

    public Person(String name) {
      this.name = name;
    }

    public void sayHi() {
      System.out.println("Hi, I'm " + name);
    }

    /* This method is abstract, meaning other classes that want to be a Person
       must define it and say what it means to relax for them.  Since
       at least one method in this class is abstract, the whole class is
       also abstract, hence the abstract in "static abstract class Person"
     */
    abstract void relax();
  }

  /** An introvert is a person who relaxes with their favorite book **/
  static class Introvert extends Person /* the superclass */ {

    String favoriteBook;

    public Introvert(String name, String favoriteBook) {
      super(name); // super means call the call we're extending
      this.favoriteBook = favoriteBook;
    }

    @Override // means we're defining a method from the superclass
    void relax() {
      System.out.println(name + " is going to read " + favoriteBook);
    }
  }

  static class Extrovert extends Person {
    String favoriteClub;

    public Extrovert(String name, String favoriteClub) {
      super(name);
      this.favoriteClub = favoriteClub;
    }

    @Override
    void relax() {
      System.out.println(name + " is going to go dance at " + favoriteClub);
    }
  }

  public static void main(String[] args) {
    // Now let's create some people and have them relax
    Person [] people = new Person[] { new Introvert("Alice", "Travels with Charley"),
                                      new Extrovert("Bob", "Freddie's Freakout Palace"),
                                      new Introvert("Carol", "To Say Nothing of the Dog"),
                                      new Extrovert("David", "Club 54")};

    // Now, loop over all the people and ask them to say hi and then to go relax.
    for(Person p : people) {
      p.sayHi();
      p.relax();
    }
  }
}

And if we run the code we get:

Hi, I'm Alice
Alice is going to read Travels with Charley
Hi, I'm Bob
Bob is going to go dance at Freddie's Freakout Palace
Hi, I'm Carol
Carol is going to read To Say Nothing of the Dog
Hi, I'm David
David is going to go dance at Club 54

Process finished with exit code 0

So here, polymorphism allowed us to define some general behavior without actually caring about how it was implemented.  The actual behavior (relaxing, as implemented via that method) took many forms.

Java has three types of polymorphism.  The above type is abstract/interface polymorphism.  We defined an abstract class that was not complete until we defined more classes that extended that original class and filled in the parts we left in.  A Java interface is similar but has no parts pre-filled in - everything has to be defined by the class extending (or, for interfaces, implementing) it.  (There are other differences between interfaces and abstract classes, but this is the only one relevant to polymorphism).

The second type of Java polymorphism is overriding polymporphism.  (Yes, the word override was used above as well, which is confusing and unfortunate).  With overriding polymorphism a class extending a superclass decides to redefine or override the behavior of a superclass, even though it wasn't asked to.  As an example, consider that we add another type of Person to our code above:

static class ReallyExtrovert extends Extrovert {
  public ReallyExtrovert(String name, String favoriteClub) {
    super(name, favoriteClub);
  }

  @Override
  public void sayHi() {
    System.out.println("HI! MY NAME IS " + name + " AND I'M REALLY EXCITED TO BE HERE.  HOW WE ALL DOING?  WOW, THAT'S A GREAT PAINTING.  WHO WANTS TO PLAY CHARADES?");
  }
}

public static void main(String[] args) {
  // Now let's create some people and have them relax
  Person [] people = new Person[] { new Introvert("Alice", "Travels with Charley"),
                                    new Extrovert("Bob", "Freddie's Freakout Palace"),
                                    new Introvert("Carol", "To Say Nothing of the Dog"),
                                    new Extrovert("David", "Club 54"),
                                    new ReallyExtrovert("Giles", "The Slide")};

  // Now, loop over all the people and ask them to say hi and then to go relax.
  for(Person p : people) {
    p.sayHi();
    p.relax();
  }
}

And if we run the code: 
Hi, I'm Alice
Alice is going to read Travels with Charley
Hi, I'm Bob
Bob is going to go dance at Freddie's Freakout Palace
Hi, I'm Carol
Carol is going to read To Say Nothing of the Dog
Hi, I'm David
David is going to go dance at Club 54
HI! MY NAME IS Giles AND I'M REALLY EXCITED TO BE HERE.  HOW WE ALL DOING?  WOW, THAT'S A GREAT PAINTING.  WHO WANTS TO PLAY CHARADES?
Giles is going to go dance at The Slide

ReallyExtrovert overrides the original definition of sayHi with its own, but its sayHi is still a valid sayHi and so works fine with all the other sayHis in the other classes.  ReallyExtrovert was not required to do this as part of any interface or abstract class, but the effect was the same.  

Also, note that in order to create this ReallyExtrovert person, we extended Extrovert and only had to define that one method, rather than extending Person and re-typing all the regular Extrovert-specific code.  We built on the previous work we did in Extrovert - this is one of the key advantages of object oriented programming and polymorphism.

The third type of polymorphism in Java is parametric polymorphism, which is a pretty advanced topic and likely won't be covered in an introductory course.  But it's really neat...

*Real world example*
The project I work on, Hadoop, is all about processing mind-bogglingly huge amounts of data.  Hadoop programs count, filter, sort, group all of this data in order to drive all the big websites you use.  But where does Hadoop store this data?  Same place as you: in files.  And those files are stored on file systems, in the same way that your laptop has a file system.  However, the Hadoop applications do not want to worry about exactly what type of file system they're running on.  They'd rather if they can run on lots of different file systems and leave it to those file systems to store the actual data however makes sense.

(Peak at the links, but don't worry about the details - the key is that there's an abstract class and lots of implementations of that abstract class)
To do this, Hadoop defines an abstract class called FileSystem:
http://bit.ly/hadoopfilesystem
that lets you do the standard file-type things: create them, delete them, write to them, move them, rename them, etc.
Applications on top of Hadoop ask for this Hadoop file system, but since it's an abstract class, they don't actually get that class.  Instead, they get an implementation that fills in those details and:
* Stores the data in Hadoop's own servers: http://bit.ly/distributedfilesystem
or
* Stores the data in Amazon's cloud: http://bit.ly/1DJufz2
or
* Stores the data in Microsoft cloud: http://bit.ly/MicrosoftAzureFileSystem
or
* Stores the data on your local disk: http://bit.ly/LocalFileSystem

Each one of those implementations knows how to talk to the specific system that backs it (Hadoop Namenode, Amazon or Microsoft's proprietary cloud, the hard disk in your laptop, etc.).

Any other system can server as a store for Hadoop data.  And once a programmer has implemented that particular type of FileSystem, the thousands of existing Hadoop applications will run immediately on it.  This is the power of polymorphism.

*Notes*
The key take-away about polymorphism is it's all about getting stuff done without worrying about how it's actually done.  This allows the work to be separated cleanly between teams and individual programmers: people writing Hadoop apps don't need to worry about how the file system works and the people writing Hadoop filesystems don't need to worry about what apps will be running on it.  Interfaces, abstract classes, overriding, etc. are all how Java happens to implement polymorphism.  Other languages do it differently (and better), but the key concept will be the same.  

