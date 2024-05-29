package cr.ac.una.unaplanilla.service;

import cr.ac.una.unaplanilla.model.Empleado;
import cr.ac.una.unaplanilla.model.EmpleadoDto;
import cr.ac.una.unaplanilla.model.TipoPlanilla;
import cr.ac.una.unaplanilla.model.TipoPlanillaDto;
import cr.ac.una.unaplanilla.util.EntityManagerHelper;
import cr.ac.una.unaplanilla.util.Respuesta;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.NoResultException;
import jakarta.persistence.NonUniqueResultException;
import jakarta.persistence.Query;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javafx.beans.property.SimpleStringProperty;

public class TipoPlanillaService {

    EntityManager em = EntityManagerHelper.getInstance().getManager();
    private EntityTransaction et;

    public Respuesta getAll() {
        try
        {
            Query qryTipoPlanilla = em.createNamedQuery("TipoPlanilla.findAll", TipoPlanilla.class);
            List<TipoPlanilla> tPlas = (List<TipoPlanilla>) qryTipoPlanilla.getResultList();
            List<TipoPlanillaDto> tplaDto = new ArrayList<>();
            for (TipoPlanilla tPla : tPlas)
            {
                tplaDto.add(new TipoPlanillaDto(tPla));
            }
            return new Respuesta(true, "", "", "AllTiposPlanilla", tplaDto);
        } catch (NoResultException ex)
        {
            return new Respuesta(false, "No existe una tipoPlanilla con el código ingresado.", "getTipoPlanilla NoResultException");
        } catch (NonUniqueResultException ex)
        {
            Logger.getLogger(TipoPlanillaService.class.getName()).log(Level.SEVERE, "Ocurrio un error al consultar la tipoPlanilla.", ex);
            return new Respuesta(false, "Ocurrio un error al consultar la tipoPlanilla.", "getTipoPlanilla NonUniqueResultException");
        } catch (Exception ex)
        {
            Logger.getLogger(TipoPlanillaService.class.getName()).log(Level.SEVERE, "Error obteniendo la tipoPlanilla ", ex);
            return new Respuesta(false, "Error obteniendo la tipoPlanilla.", "getTipoPlanilla " + ex.getMessage());
        }
    }

    public Respuesta getTipoPlanilla(Long id) {
        try
        {
            Query qryTipoPlanilla = em.createNamedQuery("TipoPlanilla.findByTplaId", TipoPlanilla.class);
            qryTipoPlanilla.setParameter("tplaId", id);
            TipoPlanillaDto tipoPlanillaDto = new TipoPlanillaDto((TipoPlanilla) qryTipoPlanilla.getSingleResult());
            return new Respuesta(true, "", "", "TipoPlanilla", tipoPlanillaDto);
        } catch (NoResultException ex)
        {
            return new Respuesta(false, "No existe una tipoPlanilla con el código ingresado.", "getTipoPlanilla NoResultException");
        } catch (NonUniqueResultException ex)
        {
            Logger.getLogger(TipoPlanillaService.class.getName()).log(Level.SEVERE, "Ocurrio un error al consultar la tipoPlanilla.", ex);
            return new Respuesta(false, "Ocurrio un error al consultar la tipoPlanilla.", "getTipoPlanilla NonUniqueResultException");
        } catch (Exception ex)
        {
            Logger.getLogger(TipoPlanillaService.class.getName()).log(Level.SEVERE, "Error obteniendo la tipoPlanilla [" + id + "]", ex);
            return new Respuesta(false, "Error obteniendo la tipoPlanilla.", "getTipoPlanilla " + ex.getMessage());
        }
    }

    public Respuesta guardarTipoPlanilla(TipoPlanillaDto tipoPlanillaDto) {
        try
        {
            et = em.getTransaction();
            et.begin();
            TipoPlanilla tipoPlanilla;
            if (tipoPlanillaDto.getId() != null && tipoPlanillaDto.getId() > 0)
            {
                tipoPlanilla = em.find(TipoPlanilla.class, tipoPlanillaDto.getId());
                if (tipoPlanilla == null)
                {
                    return new Respuesta(false, "No se encontro en la tipoPlanilla a guardar", "guardarTipoPlanilla noResultExeption");
                }
                tipoPlanilla.actualizar(tipoPlanillaDto);
                tipoPlanilla = em.merge(tipoPlanilla);
            } else
            {
                tipoPlanilla = new TipoPlanilla(tipoPlanillaDto);
                em.persist(tipoPlanilla);
            }
            et.commit();
            return new Respuesta(true, "", "", "TipoPlanilla", new TipoPlanillaDto(tipoPlanilla));

        } catch (Exception ex)
        {
            et.rollback();
            Logger.getLogger(TipoPlanillaService.class.getName()).log(Level.SEVERE, "Error guardando la TipoPlanilla ", ex);
            return new Respuesta(false, "Error guardando la TipoPlanilla.", "guardarTipoPlanilla" + ex.getMessage());
        }
    }

    public Respuesta eliminarTipoPlanilla(Long id) {
        try
        {
            et = em.getTransaction();
            et.begin();
            TipoPlanilla tipoPlanilla;
            if (id != null && id > 0)
            {
                tipoPlanilla = em.find(TipoPlanilla.class, id);
                if (tipoPlanilla == null)
                {
                    return new Respuesta(false, "No se encontro una tipoPlanilla a eliminar", "eliminarTipoPlanilla noResultExeption");
                }

                em.remove(tipoPlanilla);
            } else
            {
                return new Respuesta(false, "Favor consultar la tipoPlanilla a eliminar", "");

            }
            et.commit();
            return new Respuesta(true, "", "");

        } catch (Exception ex)
        {
            et.rollback();
            Logger.getLogger(TipoPlanillaService.class.getName()).log(Level.SEVERE, "Error eliminando la tipoPlanilla ", ex);
            return new Respuesta(false, "Error elimanando la tipoPlanilla.", "eliminarTipoPlanilla" + ex.getMessage());
        }

    }

    public Respuesta getTiposPlanillaFiltros(String codigo, String descripcion, String planillasXMes) {
        try
        {
            Query query = em.createNamedQuery("TipoPlanilla.findByFilters", TipoPlanilla.class);
            query.setParameter("codigo", ("%" + codigo + "%").toUpperCase());
            query.setParameter("descripcion", "%" + descripcion + "%");
            query.setParameter("planillasXMes", "%" + planillasXMes + "%");
            List<TipoPlanilla> tiposPlanilla = (List<TipoPlanilla>) query.getResultList();
            List<TipoPlanillaDto> tiposPlanillaDto = new ArrayList<>();
            for (TipoPlanilla tipoPlanilla : tiposPlanilla)
            {
                tiposPlanillaDto.add(new TipoPlanillaDto(tipoPlanilla));
            }
            return new Respuesta(true, "", "", "TiposPlanilla", tiposPlanillaDto);
        } catch (NoResultException ex)
        {
            return new Respuesta(false, "No existen TiposPlanilla con esos detalles.", "getTiposPlanilla NoResultException");
        } catch (Exception ex)
        {
            Logger.getLogger(TipoPlanillaService.class.getName()).log(Level.SEVERE, "Error obteniendo tiposPlanillas.", ex);
            return new Respuesta(false, "Error obteniendo TiposPlanillas.", "getTiposPlanilla " + ex.getMessage());
        }
    }
}
