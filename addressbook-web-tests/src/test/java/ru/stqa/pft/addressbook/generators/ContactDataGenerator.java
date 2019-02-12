package ru.stqa.pft.addressbook.generators;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.ParameterException;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import ru.stqa.pft.addressbook.model.ContactData;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

public class ContactDataGenerator {

    @Parameter(names = "-c", description = "Contact count")
    public int count;

    @Parameter(names = "-f", description = "Target file")
    public String file;

    @Parameter(names = "-d", description = "Data format")
    public String format;

    public static void main(String[] args) throws IOException {
        ContactDataGenerator generator = new ContactDataGenerator();
        JCommander jCommander = new  JCommander(generator);
        try {
            jCommander.parse(args);
        } catch (ParameterException ex) {
            jCommander.usage();
            return;
        }
        generator.run();

    }

    private List<ContactData> generateContacts(int count) {
            List<ContactData> contacts = new ArrayList<ContactData>();
            for (int i = 0; i < count; i++) {
                contacts.add(new ContactData().withFirstName(String.format("firstname %s", i))
                        .withLastName(String.format("lastname %s", i)).withNickname(String.format("nickname %s", i))
                        .withTitle(String.format("title %s", i)).withCompany(String.format("company %s", i))
                        .withAddress(String.format("Address 775 Westminster Avenue APT D5 %s", i))
                        .withMobile(String.format("1212121212 %s", i)).withEmail(String.format("test@mail.com %s", i)));
            }
            return contacts;
        }

    private void run() throws IOException {
        List<ContactData> contacts = generateContacts(count);
        if (format.equals("csv")) {
            saveAsCsv(contacts, new File (file));
        } else if (format.equals("json")) {
            saveAsJson(contacts, new File (file));
        } else {
            System.out.println("Unrecognized format " + format);
        }
    }

    private void saveAsJson(List<ContactData> contacts, File file) throws IOException {
        Gson gson = new GsonBuilder().setPrettyPrinting().excludeFieldsWithoutExposeAnnotation().create();
        String json = gson.toJson(contacts);
        Writer writer = new FileWriter(file);
        writer.write(json);
        writer.close();
    }


    private void saveAsCsv(List<ContactData> contacts, File file) throws IOException {
        Writer writer = new FileWriter(file);
        for (ContactData contact : contacts) {
            writer.write(String.format("%s;%s;%s;%s;%s;%s;%s;%s\n", contact.getFirstName(), contact.getLastName()
                    , contact.getNickname(), contact.getTitle(), contact.getCompany(), contact.getAddress()
                    , contact.getMobile(), contact.getEmail()));
        }
        writer.close();
    }
}
