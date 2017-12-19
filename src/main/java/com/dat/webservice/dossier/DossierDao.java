package com.dat.webservice.dossier;

import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;

import java.util.Collection;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;

public class DossierDao {

    private Map<String,Dossier> dossiers;
    private ListeningExecutorService service;

    DossierDao() {
        dossiers = new ConcurrentHashMap<String,Dossier>();
        service = MoreExecutors.listeningDecorator(Executors.newFixedThreadPool(10));
    }

    Collection<Dossier> getDossiers() {
        return(dossiers.values());
    }

    ListenableFuture<Collection<Dossier>> getDossiersAsync() {
        ListenableFuture<Collection<Dossier>> future =
                service.submit(new Callable<Collection<Dossier>>() {
                    public Collection<Dossier> call() throws Exception {
                        return getDossiers();
                    }
                });
        return(future);
    }

    Dossier getDossier(String id) {
        return(dossiers.get(id));
    }

    ListenableFuture<Dossier> getDossierAsync(final String id) {
        ListenableFuture<Dossier> future =
                service.submit(new Callable<Dossier>() {
                    public Dossier call() throws Exception {
                        return getDossier(id);
                    }
                });
        return(future);
    }

    Dossier addDossier(Dossier dossier) {
        dossier.setIdDossier(UUID.randomUUID().toString());
        dossiers.put(dossier.getIdDossier(), dossier);
        return(dossier);
    }

    ListenableFuture<Dossier> addDossierAsync(final Dossier dossier) {
        ListenableFuture<Dossier> future =
                service.submit(new Callable<Dossier>() {
                    public Dossier call() throws Exception {
                        return addDossier(dossier);
                    }
                });
        return(future);
    }

    Dossier updateDossier(Dossier dossier) {
        if (dossier.getIdDossier() != null){
            dossiers.remove(dossier.getIdDossier());
            dossiers.put(dossier.getIdDossier(), dossier);
        }
        return(dossier);
    }

    ListenableFuture<Dossier> updateDossierAsync(final Dossier dossier) {
        ListenableFuture<Dossier> future =
                service.submit(new Callable<Dossier>() {
                    public Dossier call() throws Exception {
                        return updateDossier(dossier);
                    }
                });
        return(future);
    }

    Dossier deleteDossier(final String id) {
        Dossier deleted = getDossier(id);
        dossiers.remove(id);
        return deleted;
    }

    ListenableFuture<Dossier> deleteDossierAsync(final String id) {
        ListenableFuture<Dossier> future =
                service.submit(new Callable<Dossier>() {
                    public Dossier call() throws Exception {
                        return deleteDossier(id);
                    }
                });
        return(future);
    }
}