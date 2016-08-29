# FallingWords

**How much time was invested**

7 hours 30 minutes were spent to design architecture and develop the game.

**How was the time distributed**

1. 30 minutes to think up game concept, main layers and UI.
2. 1.5h to develop the first version of layers interfaces and some entities classes.
3. 2h to implement buisness logic in the Model layer (`WordsProvider` and `GameEngine`) with unit tests.
4. 1h to implement the Presenter layer with unit tests.
5. 2h to implement the View layer.
6. 30 minutes to test and perform a little code refactoring.

**Decisions made to solve certain aspects of the game**

1. All counters are implemented via RxJava, because in my opinion it's the most convinient and clear way.
2. `WordsProvider.getWords()` method is async for more flexibility. For instance, in future a `NetworkWordsProviderImpl` can be created.
3. `GameEngine` is not a singleton for more flexibility and for testing purpose. For instance, in future user can save and restore a game.
4. After showing a word to user it is moved to the another `usedWords` list to avoid repeated usageof the word in different levels of the game.

**Decisions made because of restricted time**

1. Finish application after the end of a game. This approach allows us don't implement additional ui for restaring game.
2. Create very simple user interface.
3. Don't write UI tests. Final app was tested manually.
4. Don't write javadoc for interfaces.

**What would be the first thing to improve or add if there had been more time**

In descending priority order:

1. UI tests
2. Better coverage of unit tests
3. Javadoc
4. Better UI
5. New functionality (Saving games/results, more flexible levels, etc.)
