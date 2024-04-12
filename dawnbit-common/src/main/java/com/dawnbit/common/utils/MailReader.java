package com.dawnbit.common.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.internet.InternetAddress;
import javax.mail.search.AndTerm;
import javax.mail.search.FlagTerm;
import javax.mail.search.FromTerm;
import javax.mail.search.SearchTerm;

public class MailReader {

    private static final Map<Provider, String> providerToMailStore = new HashMap<Provider, String>();

    static {
        providerToMailStore.put(Provider.GMAIL, "imap.gmail.com");
    }

    public static Message[] getUnreadMails(Provider provider, String username, String password, String folderName, String mailFrom)
            throws MessagingException {
        Properties props = System.getProperties();
        props.setProperty("mail.store.protocol", "imaps");
        Session session = Session.getDefaultInstance(props, null);
        Store store = session.getStore("imaps");
        store.connect(providerToMailStore.get(provider), username, password);
        Folder folder = store.getFolder(folderName);
        folder.open(Folder.READ_WRITE);
        FlagTerm unseenFlagTerm = new FlagTerm(new Flags(Flags.Flag.SEEN), false);
        SearchTerm searchTerm = unseenFlagTerm;
        if (mailFrom != null) {
            FromTerm fromTerm = new FromTerm(new InternetAddress(mailFrom));
            searchTerm = new AndTerm(searchTerm, fromTerm);
        }
        return folder.search(searchTerm);
    }

    public void saveAttachment(Message message) {
        /*
         * if (message instanceof MimeBodyPart && !message.isMimeType("multipart/*")) {
         * String disp = p.getDisposition(); // many mailers don't include a
         * Content-Disposition if (disp == null ||
         * disp.equalsIgnoreCase(Part.ATTACHMENT)) { if (filename == null) filename =
         * "Attachment" + attnum++; pr("Saving attachment to file " + filename); try {
         * File f = new File(filename); if (f.exists()) // XXX - could try a series of
         * names throw new IOException("file exists"); ((MimeBodyPart) p).saveFile(f); }
         * catch (IOException ex) { pr("Failed to save attachment: " + ex); }
         * pr("---------------------------"); } }
         */
    }

    public enum Provider {
        GMAIL
    }

}
