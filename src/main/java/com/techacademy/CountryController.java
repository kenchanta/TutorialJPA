package com.techacademy;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("country") //⇐これにより、CountryController 内のすべてのURLは /country というプレフィックスを持っている。
public class CountryController {
    //メソッドを実行するサービスクラス（テーブル更新・検索）のインスタンス化（HTMLからのリクエストをそれを使って処理するため）
    private final CountryService service;
    //コンストラクタ（依存性の注入（DI））
    public CountryController(CountryService service) {
        this.service = service;
    }
    //事前準備完了

    // ----- 一覧画面 -----　
    @GetMapping("/list")
    public String getList(Model model) {
        model.addAttribute("countrylist", service.getCountryList());
        return "country/list";
    }

    // ----- 詳細画面（「新規登録」と「更新」） -----
    @GetMapping(value= {"/detail", "/detail/{code}"})
    //detail にアクセスした場合、通常、ブラウザがそのURLにアクセスするために GETリクエスト を送信します。このため、Springコントローラーで定義された @GetMapping("/detail") メソッドが最初に呼び出されます。
    //→その後、例えば詳細情報を更新するためにフォームを送信すると、フォームの action="/country/detail" から POSTリクエスト が送信されます。この時、@PostMapping("/detail") が呼ばれ、フォームデータがサーバーに送信され、処理が行われます。
    public String getCountry(@PathVariable(name="code", required=false) String code, Model model) {
        // codeが指定されていたら検索結果、無ければ空のクラスを設定
        Country country = code != null ? service.getCountry(code):new Country();
        //必ず Country オブジェクトをModelに登録することで、 detail.html テンプレートの方でエラーになることを防いでいる
        model.addAttribute("country", country);
        return "country/detail";
    }
    // ----- 更新（追加） -----
    @PostMapping("/detail")
    public String postCountry(@RequestParam("code") String code, @RequestParam("name") String name, @RequestParam("population") int population, Model model) {
        service.updateCountry(code, name, population);
        return "redirect:/country/list";
    }

    // ----- 削除画面 -----
    @GetMapping(value={"/delete", "/delete/{code}"})
    public String deleteCountryForm(@PathVariable(name="code", required=false) String code, Model model) {
        return "country/delete";
    }


    @PostMapping("/delete")
    //下記の("code")はcodeという名前のクエリパラメータかフォームパラメータを受け取るという意味。例えば<input type="text" name="code" th:value="${code}">のname=code の部分。
    public String deleteCountry(@RequestParam("code") String code, Model model) {
        service.deleteCountry(code);
        return "redirect:/country/list";
    }

}