package com.dat.webservice.dossier;

import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;
import com.fasterxml.jackson.jaxrs.xml.JacksonXMLProvider;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.server.ResourceConfig;

/**
 * Created by acasado on 4/5/2017.
 */
public class DossierApplication extends ResourceConfig {


    DossierApplication(final DossierDao dao) {
        JacksonJsonProvider json = new JacksonJsonProvider().
                configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false).
                configure(SerializationFeature.INDENT_OUTPUT, true);


        JacksonXMLProvider xml = new JacksonXMLProvider().
                configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false).
                configure(SerializationFeature.INDENT_OUTPUT, true);

        packages("com.dat.webservice.dossier");
        register(new AbstractBinder() {
            protected void configure() {
                bind(dao).to(DossierDao.class);
            }
        });
        register(json);
        register(xml);
    }
}
