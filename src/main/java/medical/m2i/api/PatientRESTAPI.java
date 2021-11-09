package medical.m2i.api;


import entities.PatientEntity;
import medical.m2i.dao.DbConnection;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/patient")
public class PatientRESTAPI {

    EntityManager em = DbConnection.getInstance();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("")
    public List<PatientEntity> getAll() {
        List<PatientEntity> p = em.createNativeQuery("SELECT * FROM patient", PatientEntity.class).getResultList();
        return p;
    }


    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{id}")
    public PatientEntity getOne( @PathParam("id") int id) {
        return em.find(PatientEntity.class, id);
    }

    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/delete/{id}")
    public void deleteOne( @PathParam("id") int id) {
        PatientEntity p = em.find(PatientEntity.class, id);
        EntityTransaction tx = em.getTransaction();
        // Début des modifications
        try {
            tx.begin();
            em.remove(p);
            tx.commit();
            System.out.println("id du patient suppriler : " + id);
        } catch (Exception e) {
            tx.rollback();
        } finally {}
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("")
    public void addPatient( PatientEntity p) {
        EntityTransaction tx = em.getTransaction();
        // Début des modifications
        try {
            tx.begin();
            em.persist(p);
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {}
    }
}
