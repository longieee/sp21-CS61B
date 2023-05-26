package capers;

import java.io.File;
import java.io.IOException;

import static capers.Utils.*;

/**
 * A repository for Capers
 *
 * @author longieee
 * The structure of a Capers Repository is as follows:
 * <p>
 * .capers/ -- top level folder for all persistent data in your lab12 folder
 * - dogs/ -- folder containing all the persistent data for dogs
 * - story -- file containing the current story
 */
public class CapersRepository {
    /**
     * Current Working Directory.
     */
    static final File CWD = new File(System.getProperty("user.dir"));

    /**
     * Main metadata folder.
     */
    static final File CAPERS_FOLDER = join(CWD, ".capers");

    /**
     * Story persistent file
     */
    static final File STORY_FILE = join(CAPERS_FOLDER, "story");

    /**
     * Does the required filesystem operations to allow for persistence.
     * (creates any necessary folders or files)
     */
    public static void setupPersistence() {
        if (!CAPERS_FOLDER.exists()) {
            CAPERS_FOLDER.mkdir();
        }
        if (!Dog.DOG_FOLDER.exists()) {
            Dog.DOG_FOLDER.mkdir();
        }
        if (!STORY_FILE.exists()) {
            try {
                STORY_FILE.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * Appends the first non-command argument in args
     * to a file called `story` in the .capers directory.
     *
     * @param text String of the text to be appended to the story
     */
    public static void writeStory(String text) {
        // Read the story stored in file so far, and keep it in
        // a local variable
        String storySoFar = readContentsAsString(STORY_FILE);
        // Append to current story the new text
        if (storySoFar.isBlank()) {
            storySoFar = text;
        } else {
            storySoFar += "\n" + text;
        }
        System.out.println(storySoFar);
        // Persists the story
        writeContents(STORY_FILE, storySoFar);
    }

    /**
     * Creates and persistently saves a dog using the first
     * three non-command arguments of args (name, breed, age).
     * Also prints out the dog's information using toString().
     */
    public static void makeDog(String name, String breed, int age) {
        // Create a new Dog from information received
        Dog newDog = new Dog(name, breed, age);
        // Persists dog to file
        newDog.saveDog();
        // Print out dog's information
        System.out.println(newDog.toString());
    }

    /**
     * Advances a dog's age persistently and prints out a celebratory message.
     * Also prints out the dog's information using toString().
     * Chooses dog to advance based on the first non-command argument of args.
     *
     * @param name String name of the Dog whose birthday we're celebrating.
     */
    public static void celebrateBirthday(String name) {
        Dog dog = Dog.fromFile(name);
        dog.haveBirthday();
        dog.saveDog();
    }
}
