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

@Path("/ville")
public class VilleRESTAPI {

    EntityManager em = DbConnection.getInstance();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("")
    public List<VilleEntity> getAll() {
        List<VilleEntity> v = em.createNativeQuery("SELECT * FROM ville", VilleEntity.class).getResultList();
        return v;
    }

    private VilleEntity getVille(int id) {
        VilleEntity v = em.find(VilleEntity.class, id);

        if( v == null ){
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        }
        return v;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{id}")
    public VilleEntity getOne( @PathParam("id") int id) throws  Exception {
        return getVille(id);
    }

    @DELETE
    @Path("/delete/{id}")
    public void deleteVille(@PathParam("id") int id) {
        EntityTransaction tx = em.getTransaction();
        // Début des modifications
        try {
            tx.begin();
            em.remove(getVille(id));
            tx.commit();
            System.out.println("id de la ville supprimer : " + id);
        } catch (Exception e) {
            tx.rollback();
            System.out.println("Exception : " + e.getMessage());
            throw e;
        } finally {}
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("")
    public void addVille( VilleEntity v) {
        EntityTransaction tx = em.getTransaction();
        // Début des modifications
        try {
            tx.begin();
            em.persist(v);
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {}
    }

    @PUT
    @Path("/{id}")
    @Consumes( MediaType.APPLICATION_JSON )
    public void updateVille( @PathParam("id") int id , VilleEntity vparam ){

        VilleEntity v = getVille(id);

        v.setNom( vparam.getNom() );
        v.setCodePostal( vparam.getCodePostal() );
        v.setPays( vparam.getPays() );

        EntityTransaction tx = em.getTransaction();
        // Début des modifications
        try {
            tx.begin();
            em.persist(v);
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            System.out.println("Exception " + e.getMessage() );
            throw e;
        }
    }
}
