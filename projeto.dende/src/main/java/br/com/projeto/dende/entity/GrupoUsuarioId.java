package br.com.projeto.dende.entity;

import java.io.Serializable;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GrupoUsuarioId implements Serializable {

	private static final long serialVersionUID = 1L;

	@Column(name = "id_grupo")
	private Integer idGrupo;

	@Column(name = "id_usuario")
	private Integer idUsuario;

	// IMPORTANTE: Implementar equals e hashCode para chave composta
	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		GrupoUsuarioId that = (GrupoUsuarioId) o;
		return Objects.equals(idGrupo, that.idGrupo) && Objects.equals(idUsuario, that.idUsuario);
	}

	@Override
	public int hashCode() {
		return Objects.hash(idGrupo, idUsuario);
	}

}
