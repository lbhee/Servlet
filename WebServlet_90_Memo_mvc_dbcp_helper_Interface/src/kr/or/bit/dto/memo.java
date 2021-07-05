package kr.or.bit.dto;

// DB테이블에 있는 데이터를 담을수 있는 클래스(DTO) -> DB테이블과 1:1로 만든다.

/*
	create table memo(
		id varchar2(15) not null,
		email varchar2(20) not null,
		content varchar2(100)
	);
	
	select id, email, content from memo where id=?  --> 데이터1건을 담을수 있는 클래스(dto)
	
	>> hong, hong@naver.com, 방가방가
	
	DTO는 DB에 있는 테이블 구조와 동일하게 만들면된다.(컬럼명까지) -> [자동화] 이점을 살릴 수 있다.
	
	로직없이 순수한 getter, setter만 가진 클래스
*/

public class memo {
	private String id;
	private String email;
	private String content;
	
	public memo() {
		
	}

	public memo(String id, String email, String content) {
		super();
		this.id = id;
		this.email = email;
		this.content = content;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Override
	public String toString() {
		return "memo [id=" + id + ", email=" + email + ", content=" + content + "]";
	}
	
}
