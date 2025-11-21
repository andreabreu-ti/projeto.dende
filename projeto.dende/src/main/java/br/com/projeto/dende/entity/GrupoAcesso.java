package br.com.projeto.dende.entity;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "cad_grupoacesso")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GrupoAcesso {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_grupo")
	private Integer idGrupo;

	@Column(name = "desc_grupo", length = 100, nullable = false)
	private String descGrupo;

	@Column(name = "situacao", length = 1, nullable = false, columnDefinition = "CHAR(1)")
	@Builder.Default
	private String situacao = "A";

	@Column(name = "data_criacao", nullable = false, updatable = false)
	@Builder.Default
	private LocalDateTime dataCriacao = LocalDateTime.now();

	@Column(name = "data_alteracao")
	private LocalDateTime dataAlteracao;

	@OneToMany(mappedBy = "grupoAcesso", cascade = CascadeType.ALL, orphanRemoval = true)
	@Builder.Default
	private Set<GrupoUsuario> gruposUsuario = new HashSet<>();

	@PreUpdate
	public void preUpdate() {
		this.dataAlteracao = LocalDateTime.now();
	}

	// IMPORTANTE: Sobrescrever hashCode e equals APENAS com o ID
	// Isso evita ConcurrentModificationException com coleções do Hibernate
	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		GrupoAcesso that = (GrupoAcesso) o;
		return Objects.equals(idGrupo, that.idGrupo);
	}

	@Override
	public int hashCode() {
		return Objects.hash(idGrupo);
	}

	public Object getIdGrupo() {
		// TODO Auto-generated method stub
		return null;
	}

}
