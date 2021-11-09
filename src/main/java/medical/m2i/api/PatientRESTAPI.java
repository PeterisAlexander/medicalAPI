package medical.m2i.api;


import entities.PatientEntity;
import entities.VilleEntity;
import medical.m2i.dao.DbConnection;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
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

    private PatientEntity getPatient(int id) {
        PatientEntity p = em.find(PatientEntity.class, id);

        if( p == null ){
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        }
        return p;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{id}")
    public PatientEntity getOne( @PathParam("id") int id) {
        return em.find(PatientEntity.class, id);
    }

    @DELETE
    @Path("/delete/{id}")
    public void deletePatient( @PathParam("id") int id) {
        EntityTransaction tx = em.getTransaction();
        // Début des modifications
        try {
            tx.begin();
            em.remove(getPatient(id));
            tx.commit();
            System.out.println("id du patient suppriler : " + id);
        } catch (Exception e) {
            tx.rollback();
            System.out.println("Exception : " + e.getMessage());
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

    @PUT
    @Path("/{id}")
    @Consumes( MediaType.APPLICATION_JSON )
    public void updatePatient( @PathParam("id") int id , PatientEntity pparam ){

        PatientEntity p = getPatient(id);

        p.setNom(pparam.getNom());
        p.setPrenom(pparam.getPrenom());
        p.setDatenaissance(pparam.getDatenaissance());
        p.setAdresse(pparam.getAdresse());
        p.setVille(pparam.getVille());

        EntityTransaction tx = em.getTransaction();
        // Début des modifications
        try {
            tx.begin();
            em.persist(p);
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            System.out.println("Exception " + e.getMessage() );
            throw e;
        }
    }
}
