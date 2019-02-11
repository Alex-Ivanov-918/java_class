package ru.stqa.pft.addressbook.generators;

import ru.stqa.pft.addressbook.model.ContactData;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

public class ContactDataGenerator {
        public static void main(String[] args) throws IOException {
        int count = Integer.parseInt(args[0]);
        File file = new File(args[1]);

        List<ContactData> contacts = generateContacts(count);
        save(contacts, file);
        }


        private static List<ContactData> generateContacts(int count) {
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

    private static void save(List<ContactData> contacts, File file) throws IOException {
        Writer writer = new FileWriter(file);
        for (ContactData contact : contacts) {
            writer.write(String.format("%s;%s;%s;%s;%s;%s;%s;%s\n", contact.getFirstName(), contact.getLastName()
                    , contact.getNickname(), contact.getTitle(), contact.getCompany(), contact.getAddress()
                    , contact.getMobile(), contact.getEmail()));
        }
        writer.close();
    }
}
