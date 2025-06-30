package com.utp.integradorspringboot.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Representa un producto del inventario en un restaurante.
 * Cada producto pertenece a un restaurante y contiene información como nombre, cantidad,
 * categoría, fechas de vencimiento e ingreso, y estado.
 *
 * Este modelo está mapeado a la tabla 'productos' en la base de datos.
 *
 * @author Sebastian Velarde
 */

@Entity
@Table(name = "productos")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Producto {

    /**
     * Identificador único del producto.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Identificador del restaurante al que pertenece el producto.
     */
    @Column(name = "id_restaurante", nullable = false)
    private Long idRestaurante;

    /**
     * Nombre del producto.
     */
    @Column(nullable = false)
    private String nombre;

    /**
     * Cantidad disponible en stock.
     */
    @Column(nullable = false)
    private Integer cantidad;

    /**
     * Categoría del producto (e.g., perecible, bebida, otro).
     */
    @Column(nullable = false)
    private String categoria;

    /**
     * Fecha de vencimiento del producto (formato ISO).
     * Puede ser null si no aplica.
     */
    @Column(name = "fecha_vencimiento")
    private String fechaVencimiento;

    /**
     * Cantidad mínima recomendada para stock.
     * Valor por defecto es 0.
     */
    @Column(name = "cantidad_minima")
    private Integer cantidadMinima;

    /**
     * Fecha de ingreso del producto al sistema (formato ISO).
     */
    @Column(name = "fecha_ingreso")
    private String fechaIngreso;

    /**
     * Estado del producto (e.g., activo, inactivo).
     * Por defecto es 'activo'.
     */
    @Column(nullable = false)
    private String estado = "activo";
}
