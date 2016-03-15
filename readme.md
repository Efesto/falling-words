# Falling Words
## A game of few words
### March 2016

* The project has been developed during grossly 6 hours and half of work including documentation writing, design and planning

## At the origin there was the Whiteboard
### Concepts, planning, game mechanics, UI design

### UI/UX Design

* I've started with a simple whiteboard UI/UX design in which I've defined some simple principles for my game:
* The game interface should be self explanatory, the controls should be simpler as possible and the screen feedback always present
* I've decided for including two buttons, the first for communicate the correctness of the translation and the other for the opposite
  * This changed during development cycle 
* A translation going out screen without guessing has to be considered a wrong match
  * Also this changed during development cycle 
* The pace at which correct translations and wrong translations are served should be correctly tweaked
* A super simple endgame screen
* Some sounds for enhancing the user feedback

![Whiteboard](/whiteboard.jpg)

### Planning
* I've also planned some small 1 hour developing sprints with some simple stories to develop in my project
* For each sprint I've defined some priority ordered features
* I've considered, optimistically, to deliver the working game with the minimal set of features in the first 4 sprints, actually I've used 6 sprints

* **Interface UI/UX design and planning required 20 minutes circa.**

## And so came the code

The development started with the implementation of a non working interface with Android Studio design.
** This part required 45 minutes but the design proved to be solid.**
After design I've started with the effective code writing.

With TDD in mind I've chosen [Robolectric](http://robolectric.org/) as the testing framework for testing the most complicated game parts bound to Android SDK, and Mockito for dependency injection.

I've to admit that my knowledge of Robolectric is not so shining because I've always used the combination of [Calabash-Android](https://github.com/calabash/calabash-android) and AndroidTestCase so it required
some time to ambient myself and to write a first red test concerning the PlayActivity.

Anyway I've decided to use Robolectric because Calabash-Android requires long execution times, a working emulator/device and a bit of patience and the time was restricted.

**Getting confortable with the test framework required about 1 hour.**

## Planning refinement

At the end of sprint 2 I've made some whiteboard design for the part of code concerning the models.

I've established to have a model called "Translations" which, at first would have provided a translation model that contains the words and the correctness flag and only after a properly randomness of results and matches.

**Implementing the provider and tweaking the randomness took about 1 hour and half using unit testing and some hand testing.**

I've also wrote a simple unit test for testing the randomness factor in results distribution to have, on 300 extractions, around 100 corrects.

I've found the 1/3 correct translations rate to be enough stimulating from the user point of view :)

## The delays

At the second half of sprint 3 I've started implementing the "time delays" in my game.

One is the delay between the start of a round and the end by not having answered, from the user point of view is simply the word falling out from the screen.

The second is the delay between the end of a round and the start of another to allow the user to take breath (but also for keeping him concentrated).

I've started with a test that used Thread.sleep for testing the enabling and disabling of buttons and counting of score indicators (wrongs, rights, rounds played).

Driven by the tests I've implemented the delays using AsyncTasks and callbacks.

The problems started when I've encountered a slowing down of every tests given by the newly introduced delays.

Using mockito I've tried to inject some more manageable delays (aka smaller) but without success.

From here going on I've moved using mainly hand feedback because the time was running out and the game was aproaching beta testing.

**This part took 1 hour circa.**

## Ending and last refinements

**The last hour and half was used to write documentation, introducing the animation in the falling words (until this phase everything was static AKA without any kind of animations) and revisiting some small interface elements like colors**

At last I've also decided to remove the wrong button in favor of the only "That's correct" button.

Also the wrong translation falling out the screen is counted as "Correct" and the good translation doing the same is counted as "Wrong".

I've find this approach to be radically different from the previous "always say something": more interesting and more "clean".

## What I would have done with more time

My first concerns would be the automated tests because as I've left them they're mostly useless but I'm pretty sure that with a better design they could prove useful again.

After them could be great to have:

* Some sounds for feedback (no music of course)
* Some more emphatic animations or graphics for the Yeah/Nope feedback
* An End Game screen with game stats report, maybe with a small hall of fame.
* The possibility of having different languages, to choose between and having the translations by a remote service
  * maybe with something like a "Spanish vs German" screen in Street Fighter style.
* Trying another testing framework, maybe the Android Instrumented tests and/or Espresso using a fast emulator like Genymotion (instead of the, current stable, super slow Google one)
  * (I'm pretty sure my testing way will change with the newer Google emulator but who can say?)















