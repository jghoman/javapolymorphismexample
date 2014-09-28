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

    abstract void relax();
    /* This method is abstract, meaning other classes that want to be a Person
       must define it and say what it means to relax for them.  Since
       at least one method in this class is abstract, the whole class is
       also abstract, hence the abstract in "static abstract class Person"
     */
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
}
