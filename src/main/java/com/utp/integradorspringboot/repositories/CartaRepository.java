/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.utp.integradorspringboot.repositories;

import com.utp.integradorspringboot.models.Carta;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author jcerv
 */
public interface CartaRepository extends JpaRepository<Carta, Long> {
}
