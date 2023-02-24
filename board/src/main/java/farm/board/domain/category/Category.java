package farm.board.domain.category;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Entity //JPA Entity임을 나타내는 어노테이션
@Getter // Category 클래스의 필드에 대한 Getter 메서드 제공
@NoArgsConstructor(access = AccessLevel.PROTECTED) // 기본 생성자 제공 (PROTECTED 를 통해 외부 인스턴스화 방지)
public class Category {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id")
    private Long id;

    @Column(length = 30, nullable = false)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY) //하나의 부모가 여러 개의 자식 카테고리를 가질 수 있음. 지연 로딩
    @JoinColumn(name = "parent_id")
    @OnDelete(action = OnDeleteAction.CASCADE) // 부모 카테고리가 살제될 때 부모 카테고리에 속하는 모든 카테고리 삭제
    private Category parent;

    public Category(String name, Category parent) {
        this.name = name;
        this.parent = parent;
    }
}