# Falling Words
## A game of few words

* The project has been developed during grossly 6 hours of work including documentation writing, design and planning

## At the origin there was the Whiteboard
### Concepts, planning, game mechanics, UI design

### UI/UX Design

* I've started with a simple whiteboard UI/UX design in which I've defined some simple principles for my game:
** The game interface should be self explanatory, the controls should be simpler as possible and the screen feedback always present
** I've decided for including two buttons, the first for communicate the correctness of the translation and the other for the opposite
** A translation going out screen without guessing is a wrong match
** The pace at which correct translations and wrong translations are served should be correctly tweaked
** A super simple endgame screen
** Some sounds for enhancing the user feedback

![Whiteboard](/whiteboard.jpg)

### Planning
* I've also planned some small 1 hour developing sprints with some simple stories to develop in my project
** For each sprint I've defined some priority ordered features
* I've considered, optimistically, to deliver the working game with the minimal set of features in the first 4 sprints, actually I've used 6 sprints

* **Interface UI/UX design and planning required 20 minutes circa.**

## And so came the code

The development started with the implementation of a non working interface with Android Studio design.
**This part required 45 minutes but the design proven to be solid.**
After design I've started with the effective code writing.
With TDD in mind I've chosen [Roboelectric](http://robolectric.org/) as the testing framework for testing the most complicated game parts bound to Android SDK and Mockito for dependency injection.
I've to admit that my knowledge of Roboelectric is not so shining because I've always used the combination of [Calabash-Android](https://github.com/calabash/calabash-android) and AndroidTestCase so it required
some time to ambient myself and write a first red test concerning the PlayActivity.
But I've decided to use Roboelectric anyway because Calabash-Android requires long execution times, a working emulator/device and a bit of patience and the time was restricted.
**Getting confortable with the test framework required 1 hour circa.**

## Planning refinement

At the end of sprint 2 I've made some whiteboard design for the part of code concerning the models.
I've established to have a model called "Translations" which, at first would have provided a translation model that contains the words and the correctness flag and only after a properly
randomness of results and matches.
**Implementing the provider and tweaking the randomness took about 1 hour and half using unit testing and some hand testing.**
I've also wrote a simple unit test for testing the randomness distribution of correct results on 300 extractions to be 100 gross.
I've found the 1/3 correct translations rate to be enough stimulating from the user point of view :)

## The delays

At the second half of sprint 3 I've started implementing the "time delays" in my game.
One is the delay between the start of a round and the end by not having answered, from the user point of view is simply the word falling out from the screen.
The second is the delay between the end of a round and the start of another for keeping the user take breath (but not too much)
I've started with a test that used Thread.sleep for testing the enabling and disabling of buttons and counting of score indicators (wrongs, rights, rounds played)
Driven by the tests I've implemented the delays using AsyncTasks and callbacks.
The problems started when I've encountered a slowing down of every tests given by the newly introduced delays.
Using mockito I've tried to inject some more manageable delays (aka smaller) but without success.
From here going on I've moved using mainly hand feedback because the time was running out and the game began to take be something to beta testing.
**This part took 1 hour circa.**

## Ending and last refinements

**The last hour was used to write documentation, introducing the animation in the falling words (until this phase everything was static) and revisiting some small interface elements like colors**
At last I've also decided to remove the wrong button in favor of the only "That's correct" button.
Also the wrong translation falling out the screen is counted as "Correct" and the good translation doing the same is counted as "Wrong".
I've find this approach to be radically different from the previous "always say something" approach in best.

## What I would have done with more time

My first concerns would be about the automated tests because as I've left them they're mostly useless but I'm pretty sure that with a better design they could prove useful again.
After them could be great to have:
* Some sounds for feedback (no music of course)
* An End Game screen with game stats report, maybe with a small hall of fame.
* The possibility of having different languages, to choose between them and having the translations by a remote service
** maybe with something like a "Spanish vs German" screen in Street Fighter style.















