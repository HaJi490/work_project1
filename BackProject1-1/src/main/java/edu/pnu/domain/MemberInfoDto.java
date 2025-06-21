package edu.pnu.domain;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class MemberInfoDto {
	private String id;
	private String username;
}
