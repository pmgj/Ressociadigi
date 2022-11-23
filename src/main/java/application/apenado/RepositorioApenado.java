package application.apenado;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "apenados", path = "apenados")
public interface RepositorioApenado extends JpaRepository<Apenado, String> {
	//Nome
	@Query(value = "SELECT * FROM Apenado WHERE LOWER(Apenado.NOME) LIKE %:nome%", nativeQuery = true)
	Page<Apenado> findByNome(String nome, Pageable pageable);
	
	//CPF
	@Query(value = "SELECT * FROM Apenado WHERE LOWER(Apenado.CPF) LIKE %:cpf%", nativeQuery = true)
	Page<Apenado> findByCpf(String cpf, Pageable pageable);
	
	//Data de Nascimento 
	@Query(value = "SELECT * FROM Apenado WHERE LOWER(Apenado.DATA_NASCIMENTO) LIKE %:dataNascimento%", nativeQuery = true)
	Page<Apenado> findByDataNascimento(String dataNascimento, Pageable pageable);
	
	//Nome da Mãe
	@Query(value = "SELECT * FROM Apenado WHERE LOWER(Apenado.NOME_DA_MAE) LIKE %:nomeDaMae%", nativeQuery = true)
	Page<Apenado> findByNomeDaMae(String nomeDaMae, Pageable pageable);
	
	//Contato
	@Query(value = "SELECT * FROM Apenado WHERE LOWER(Apenado.TELEFONE) LIKE %:telefone%", nativeQuery = true)
	Page<Apenado> findByTelefone(String telefone, Pageable pageable);
}