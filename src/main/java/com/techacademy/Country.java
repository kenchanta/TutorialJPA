package com.techacademy;

//Spring JPAのアノテーション
import jakarta.persistence.Entity;//エンティティクラス（データベースのテーブルにマッピングするクラス）であることを示す
import jakarta.persistence.Id;//主キーであることを示す
import jakarta.persistence.Table;//対応するテーブル名を指定

//Lombokのアノテーション
import lombok.AllArgsConstructor;//べてのフィールドを引数に持つコンストラクタを生成
import lombok.Data;//「getter/setter、toString、hashCode、equals」のメソッドを生成
import lombok.NoArgsConstructor; //引数を持たないコンストラクタを生成

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="country")

public class Country {
    @Id
    private String code;
    private String name;
    private int population;
}