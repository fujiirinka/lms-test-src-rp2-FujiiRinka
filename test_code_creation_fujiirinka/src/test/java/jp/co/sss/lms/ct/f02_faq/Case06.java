package jp.co.sss.lms.ct.f02_faq;

import static jp.co.sss.lms.ct.util.WebDriverUtils.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

/**
 * 結合テスト よくある質問機能
 * ケース06
 * @author holy
 */
@TestMethodOrder(OrderAnnotation.class)
@DisplayName("ケース06 カテゴリ検索 正常系")
public class Case06 {

	/** 前処理 */
	@BeforeAll
	static void before() {
		createDriver();
	}

	/** 後処理 */
	@AfterAll
	static void after() {
		closeDriver();
	}

	@Test
	@Order(1)
	@DisplayName("テスト01 トップページURLでアクセス")
	void test01() {

		// URLにアクセス
		goTo("http://localhost:8080/lms");

		// 検証：トップページ
		assertEquals("ログイン | LMS", webDriver.getTitle());
		assertEquals("ログイン", webDriver.findElement(By.tagName("h2")).getText());

		// エビデンスの取得
		getEvidence(new Object() {
		});
	}

	@Test
	@Order(2)
	@DisplayName("テスト02 初回ログイン済みの受講生ユーザーでログイン")
	void test02() {

		// 初回ログイン済みのID/PWを入力、クリック
		webDriver.findElement(By.id("loginId")).sendKeys("StudentAA02");
		webDriver.findElement(By.id("password")).sendKeys("StudentAA02");
		webDriver.findElement(By.className("btn")).click();

		// 検証：コース詳細画面への遷移
		assertEquals("コース詳細 | LMS", webDriver.getTitle());

		// エビデンスの取得
		getEvidence(new Object() {
		});
	}

	@Test
	@Order(3)
	@DisplayName("テスト03 上部メニューの「ヘルプ」リンクからヘルプ画面に遷移")
	void test03() {
		// 上部メニュー「機能」から「ヘルプ」をクリック
		webDriver.findElement(By.linkText("機能")).click();
		webDriver.findElement(By.linkText("ヘルプ")).click();

		// 検証：ヘルプ画面への遷移
		assertEquals("ヘルプ | LMS", webDriver.getTitle());

		// エビデンスの取得
		getEvidence(new Object() {
		});
	}

	@Test
	@Order(4)
	@DisplayName("テスト04 「よくある質問」リンクからよくある質問画面を別タブに開く")
	void test04() {

		// 「よくある質問」をクリック
		webDriver.findElement(By.linkText("よくある質問")).click();

		// タブの切り替え
		Object[] windowHandles = webDriver.getWindowHandles().toArray();
		webDriver.switchTo().window((String) windowHandles[1]);

		// 検証：よくある質問画面への遷移
		assertEquals("よくある質問 | LMS", webDriver.getTitle());

		// エビデンスの取得
		getEvidence(new Object() {
		});
	}

	@Test
	@Order(5)
	@DisplayName("テスト05 カテゴリ検索で該当カテゴリの検索結果だけ表示")
	void test05() {

		// カテゴリをクリック、スクロール
		webDriver.findElement(By.linkText("【研修関係】")).click();
		scrollTo("500");

		// 待ち処理
		visibilityTimeout(By.className("mb10"), 10);

		// 検証：該当カテゴリによる検索結果の表示
		List<WebElement> questionList = webDriver.findElements(By.className("mb10"));
		assertEquals("Q.キャンセル料・途中退校について", questionList.get(0).getText());
		assertEquals("Q.研修の申し込みはどのようにすれば良いですか？", questionList.get(1).getText());
		assertEquals("よくある質問 | LMS", webDriver.getTitle());

		// エビデンスの取得
		getEvidence(new Object() {
		});
	}

	@Test
	@Order(6)
	@DisplayName("テスト06 検索結果の質問をクリックしその回答を表示")
	void test06() {

		// 検索結果の質問をクリック
		List<WebElement> questionList = webDriver.findElements(By.className("mb10"));
		questionList.get(0).click();

		// 検証：質問に対応する回答の表示
		String answer = "A. 受講者の退職や解雇等、やむを得ない事情による途中終了に関してなど、事情をお伺いした上で、協議という形を取らせて頂きます。 弊社営業担当までご相談下さい。";
		assertEquals(answer, webDriver.findElement(By.id("answer-h[${status.index}]")).getText());
		assertEquals("よくある質問 | LMS", webDriver.getTitle());

		// エビデンスの取得
		getEvidence(new Object() {
		});
	}

}
