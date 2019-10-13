package per.rsf.study.javase.jdk.jndi;

import java.util.*;
import java.rmi.*;
import java.io.*;
import javax.naming.*;
import javax.ejb.*;

// This client demonstrates a sample usage of the JNDI tree

public class Client {

    private static InitialContext ctx;

    public static void main(String[] argv) {
        print("Demonstration of the usage of JNDI...");
        if (argv.length < 1) {
            print("Usage : Client <JNDI root name>\n");
            return;
        }
        try {
            print("Connecting to a JNDI service...");
            // Set JNDI environment in the properties
            Properties prop = new Properties();
            // 指定JNDI提供者
            prop.put(Context.INITIAL_CONTEXT_FACTORY,
                    "weblogic.jndi.WLInitialContextFactory");
            prop.put(Context.PROVIDER_URL, "t3://localhost:7001");
            /* JBoss
            java.naming.factory.initial=org.jnp.interfaces.NamingContextFactory
            java.naming.provider.url=jnp://localhost:1099
            java.naming.factory.url.pkgs=org.jboss.naming:org.jnp.interfaces
             */
            // Connect to the JNDI service as specified above
            Context ctx = new InitialContext(prop);

            ctx = new InitialContext();
            print("  Connected successfully. Initial context created.\n");
            print("Getting Environment Properties...");
            print("  Properties: " + ctx.getEnvironment().toString() + "\n");
            // Adding a binding
            String name = "mary";
            String email = "mary@hotmail.com";
            print("Binding a new name: " + name + " to an object: " + email + "...");
            ctx.bind(name, email);
            print("  Object: " + email + " is bound to name: " + name + "\n");
            // Lookup a binding
            print("Looking up the name...");
            String s = (String) ctx.lookup("mary");
            print("  Found Name= mary, with email= " + s + "\n");
            // Delete a binding
            print("Unbinding the name...");
            ctx.unbind("mary");
            print("  Name is unbound successfully!\n");
            print("Spanning JNDI context bindings...");
            spanJNDI(argv[0]);
            print("\n");
            // Lookup a "deleted" binding
            print("Lookup for the unbound name...error expected");
            s = (String) ctx.lookup("mary");
            print("  Found Name= mary, with email= " + s);

        } catch (CommunicationException e) {
            print("**ERROR: Failed to connect with the JNDI server." +
                    "Startup the App Server, and run again.." + e);
        } catch (Exception e) {
            print("**ERROR: An unexpected exception occurred..." + e);
        } finally {
            if (ctx != null) {
                try {
                    print("Unbinding the name...");
                    ctx.unbind("mary");
                    ctx.close();
                    print("Connection to JNDI is closed successfully.");
                } catch (NamingException e) {
                    print("**ERROR: Failed to close context due to: " + e);
                }
            }
        }
    }

    private static void spanJNDI(String name) {
        try {
            ctx = new InitialContext();
            NamingEnumeration bindList = ctx.listBindings(name);
            // Go through each item in list
            while (bindList != null && bindList.hasMore()) {
                Binding bd = (Binding) bindList.next();
                print("  " + bd.getName() + ": " + bd.getClassName() + ": " + bd.getObject());
                spanJNDI(bd.getName());
            }
        } catch (NamingException ignored) {

        }
    }

    private static void print(String s) {
        System.out.println(s);
    }
}
