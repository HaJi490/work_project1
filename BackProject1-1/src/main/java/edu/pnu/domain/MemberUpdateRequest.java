package edu.pnu.domain;

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

public class MemberUpdateRequest {
	private String username;
	private String currentpwd;
	private String newpwd;
}
