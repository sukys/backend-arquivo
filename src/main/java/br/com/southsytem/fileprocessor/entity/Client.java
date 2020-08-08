package br.com.southsytem.fileprocessor.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper=true)
public class Client extends BaseEntity {

	private String cnpj;
	
	private String name;
		
	private String businessArea;

}
