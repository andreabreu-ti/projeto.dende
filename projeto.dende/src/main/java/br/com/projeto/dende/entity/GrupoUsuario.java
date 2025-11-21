package br.com.projeto.dende.entity;

import java.time.LocalDateTime;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "cad_grupousuario")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GrupoUsuario {

	@EmbeddedId
	private GrupoUsuarioId id;

	@ManyToOne
	@MapsId("idGrupo")
	@JoinColumn(name = "id_grupo")
	private GrupoAcesso grupoAcesso;

	@ManyToOne
	@MapsId("idUsuario")
	@JoinColumn(name = "id_usuario")
	private Usuario usuario;

	@Column(name = "data_associacao", nullable = false)
	private LocalDateTime dataAssociacao;

	@Column(name = "status_associacao", length = 1, nullable = false, columnDefinition = "CHAR(1)")
	private String statusAssociacao = "A";
	

	public GrupoUsuario(GrupoAcesso grupoAcesso, Usuario usuario, LocalDateTime dataAssociacao,
			String statusAssociacao) {
		this.grupoAcesso = grupoAcesso;
		this.usuario = usuario;
		this.id = new GrupoUsuarioId();
		this.dataAssociacao = dataAssociacao;
		this.statusAssociacao = statusAssociacao;
	}

	// IMPORTANTE: Sobrescrever hashCode e equals APENAS com o ID composto
	// Isso evita ConcurrentModificationException com coleções do Hibernate
	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		GrupoUsuario that = (GrupoUsuario) o;
		return Objects.equals(id, that.id);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

}
