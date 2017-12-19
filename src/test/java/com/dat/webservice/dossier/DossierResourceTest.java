package com.dat.webservice.dossier;

import org.glassfish.jersey.test.JerseyTest;
import org.junit.Before;
import org.junit.Test;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;

import static junit.framework.Assert.assertNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Created by casaanto on 3/30/2017.
 */
public class DossierResourceTest extends JerseyTest {

    private String dossier1_id;
    private String dossier2_id;

    protected Application configure() {
        //enable(TestProperties.LOG_TRAFFIC);
        //enable(TestProperties.DUMP_ENTITY);
        final DossierDao dao = new DossierDao();
        return new DossierApplication(dao);
    }

    protected Response addDossier(String nameDossier, String idDossier,
                                  String countryDossier, Date creationYearDossier,
                                  String... extras) {
        HashMap<String,Object> dossier = new HashMap<String,Object>();
        dossier.put("nameDossier",nameDossier);
        dossier.put("idDossier",idDossier);
        dossier.put("countryDossier",countryDossier);
        dossier.put("creationYearDossier",creationYearDossier);

        if (extras != null) {
            int count = 1;
            for (String s : extras) {
                dossier.put("extra"+ count++, s);
            }
        }

        Entity<HashMap<String,Object>> dossierEntity = Entity.entity(dossier, MediaType.APPLICATION_JSON_TYPE);
        return(target("dossiers").request().post(dossierEntity));
    }

    protected Response updateDossier(String nameDossier, String idDossier,
                                  String countryDossier, Date creationYearDossier,
                                  String... extras) {
        HashMap<String,Object> dossier = new HashMap<String,Object>();
        dossier.put("nameDossier",nameDossier);
        dossier.put("idDossier",idDossier);
        dossier.put("countryDossier",countryDossier);
        dossier.put("creationYearDossier",creationYearDossier);

        if (extras != null) {
            int count = 1;
            for (String s : extras) {
                dossier.put("extra"+ count++, s);
            }
        }

        Entity<HashMap<String,Object>> dossierEntity = Entity.entity(dossier, MediaType.APPLICATION_JSON_TYPE);
        return(target("dossiers").request().put(dossierEntity));
    }

    @Before
    public void setupDossiers() {
        dossier1_id = (String)toHashMap(addDossier("VW1", "Radeon", "de", new Date(), "1234")).get("idDossier");
        dossier2_id = (String)toHashMap(addDossier("VW2", "Golf", "de", new Date(), "2345")).get("idDossier");
    }

    @Test
    public void testAddDossier() throws Exception {
        Date thisDate = new Date();
        Response response = addDossier("AlfaRomeo", "Giulia", "it", thisDate, "3456");
        assertEquals(200, response.getStatus());

        HashMap<String,Object> responseDossier = toHashMap(response);
        assertNotNull(responseDossier.get("idDossier"));
        assertEquals("AlfaRomeo",responseDossier.get("nameDossier"));
        assertEquals("it",responseDossier.get("countryDossier"));

        //AC dates are always special
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
        assertEquals(thisDate, dateFormat.parse((String)responseDossier.get("creationYearDossier")));
        assertEquals("3456",responseDossier.get("extra1"));
    }

    @Test
    public void testUpdateDossier() throws Exception {
        Date thisDate = new Date();
        Response response = updateDossier("AlfaRomeo", "Giulia", "it", thisDate, "3456");
        assertEquals(200, response.getStatus());

        HashMap<String,Object> responseDossier = toHashMap(response);
        assertNotNull(responseDossier.get("idDossier"));
        assertEquals("AlfaRomeo",responseDossier.get("nameDossier"));
        assertEquals("it",responseDossier.get("countryDossier"));

        //AC dates are always special
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
        assertEquals(thisDate, dateFormat.parse((String)responseDossier.get("creationYearDossier")));
        assertEquals("3456",responseDossier.get("extra1"));
    }

    @Test
    public void testGetDossiers() {
        Collection<HashMap<String,Object>> response = target("dossiers").request()
                .get(new GenericType<Collection<HashMap<String,Object>>>() {});
        assertEquals(2, response.size());
    }

    @Test
    public void testGetDossier() {
        HashMap<String,Object> respHashMap = toHashMap(target("dossiers").path(dossier1_id).request().get());
        assertNotNull(respHashMap);
    }

    @Test
    public void testAddExtraField() {
        Response response = addDossier("SEAT", "Leon", "es", new Date(), "1111", "hello world");
        assertEquals(200, response.getStatus());

        HashMap<String,Object> dossier = toHashMap(response);
        assertNotNull(dossier.get("idDossier"));
        assertEquals(dossier.get("extra1"),"1111");
    }

    @Test
    public void deleteDossier() {
        HashMap<String,Object> respHashMap = toHashMap(target("dossiers").path(dossier1_id).request().delete());
        assertNotNull(respHashMap);
    }

    protected HashMap<String,Object> toHashMap(Response response) {
        return(response.readEntity(new GenericType<HashMap<String,Object>>() {}));
    }

}
