package com.umpar.entities;

import java.time.LocalDate;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.umpar.enums.Pagamento;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RequiredArgsConstructor
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@Entity
@Table(name = "tb_saidas")
public class Saidas {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
	private LocalDate data;

	@NonNull
	@ManyToOne(cascade = CascadeType.REMOVE)
	@JoinColumn(name = "calcados_id")
	private Calcados calcados;

	private Integer quantidade;

	private Integer tamanho;

	private Double precoVenda;

	private Double freteVenda;

	private Double embalagem;

	@Enumerated(EnumType.STRING)
	private Pagamento pagamento;

	@Column(name = "liquido")
	private Double liquido;

	@Column(name = "valor_unidade")
	private Double unidade;
	
	
	
}




