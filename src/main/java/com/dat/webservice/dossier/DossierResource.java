package com.dat.webservice.dossier;

import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import org.glassfish.jersey.server.ManagedAsync;

import javax.ws.rs.*;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.container.Suspended;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import java.util.Collection;


@Path("/dossiers")
public class DossierResource {

    @Context
    DossierDao dao;
    // DossierDao dao = new DossierDao();
    @Context
    Request request;

    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @ManagedAsync
    public void getDossiers(@Suspended final AsyncResponse response) {
        //response.resume(dao.getDossiers());
        ListenableFuture<Collection<Dossier>> dossiersFuture = dao.getDossiersAsync();
        Futures.addCallback(dossiersFuture, new FutureCallback<Collection<Dossier>>() {
            public void onSuccess(Collection<Dossier> dossiers) {
                response.resume(dossiers);
            }
            public void onFailure(Throwable thrown) {
                response.resume(thrown);
            }
        });
    }

    @Path("/{id}")
    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @ManagedAsync
    public void getDossier(@PathParam("id") String id, @Suspended final AsyncResponse response) {
        ListenableFuture<Dossier> dossierFuture = dao.getDossierAsync(id);
        Futures.addCallback(dossierFuture, new FutureCallback<Dossier>() {
            public void onSuccess(Dossier dossier) {
                response.resume(dossier);
            }
            public void onFailure(Throwable thrown) {
                response.resume(thrown);
            }
        });
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @ManagedAsync
    public void addDossier(Dossier dossier, @Suspended final AsyncResponse response) {
        ListenableFuture<Dossier> dossierFuture = dao.addDossierAsync(dossier);
        Futures.addCallback(dossierFuture, new FutureCallback<Dossier>() {
            public void onSuccess(Dossier addedDossier) {
                response.resume(addedDossier);
            }
            public void onFailure(Throwable thrown) {
                System.out.println(thrown.getMessage());
                response.resume(thrown);
            }
        });
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @ManagedAsync
    public void updateDossier(Dossier dossier, @Suspended final AsyncResponse response) {
        ListenableFuture<Dossier> dossierFuture = dao.updateDossierAsync(dossier);
        Futures.addCallback(dossierFuture, new FutureCallback<Dossier>() {
            public void onSuccess(Dossier updatedDossier) {
                response.resume(updatedDossier);
            }
            public void onFailure(Throwable thrown) {
                System.out.println(thrown.getMessage());
                response.resume(thrown);
            }
        });
    }

    @DELETE
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @ManagedAsync
    public void deleteDossier(@PathParam("id") String id, @Suspended final AsyncResponse response) {
        ListenableFuture<Dossier> dossierFuture = dao.deleteDossierAsync(id);
        Futures.addCallback(dossierFuture, new FutureCallback<Dossier>() {
            public void onSuccess(Dossier deleteDossier) {
                response.resume(deleteDossier);
            }
            public void onFailure(Throwable thrown) {
                System.out.println(thrown.getMessage());
                response.resume(thrown);
            }
        });
    }

}
