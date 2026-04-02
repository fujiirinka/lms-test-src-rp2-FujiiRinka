package jp.co.sss.lms.ct.f05_exam;

import static jp.co.sss.lms.ct.util.WebDriverUtils.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Date;
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
 * 結合テスト 試験実施機能
 * ケース13
 * @author holy
 */
@TestMethodOrder(OrderAnnotation.class)
@DisplayName("ケース13 受講生 試験の実施 結果0点")
public class Case13 {

	/** テスト07およびテスト08 試験実施日時 */
	static Date date;

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

		pageLoadTimeout(10);

		// 検証：コース詳細画面への遷移
		assertEquals("コース詳細 | LMS", webDriver.getTitle());

		// エビデンスの取得
		getEvidence(new Object() {
		});
	}

	@Test
	@Order(3)
	@DisplayName("テスト03 「試験有」の研修日の「詳細」ボタンを押下しセクション詳細画面に遷移")
	void test03() {

		//scrollTo("200");

		// 「詳細」ボタンをクリック
		List<WebElement> submitButtonList = webDriver.findElements(By.cssSelector("input[value = '詳細']"));
		submitButtonList.get(1).click();

		pageLoadTimeout(5);

		// 検証：セクション詳細画面への遷移
		assertEquals("アルゴリズム、フローチャート 2022年10月2日", webDriver.findElement(By.tagName("h2")).getText());
		assertEquals("セクション詳細 | LMS", webDriver.getTitle());

		// エビデンスの取得
		getEvidence(new Object() {
		});
	}

	@Test
	@Order(4)
	@DisplayName("テスト04 「本日の試験」エリアの「詳細」ボタンを押下し試験開始画面に遷移")
	void test04() {

		// 「詳細」ボタンをクリック
		WebElement submitBtn = webDriver.findElement(By.cssSelector("input[value = '詳細']"));
		submitBtn.click();

		pageLoadTimeout(5);

		// 検証：試験開始画面への遷移
		assertEquals("ITリテラシー①", webDriver.findElement(By.tagName("h2")).getText());
		assertEquals("試験【ITリテラシー①】 | LMS", webDriver.getTitle());

		// エビデンスの取得
		getEvidence(new Object() {
		});

		// 遷移後URLの確認
		String url = webDriver.getCurrentUrl();
		assertEquals("http://localhost:8080/lms/exam/start", url);
	}

	@Test
	@Order(5)
	@DisplayName("テスト05 「試験を開始する」ボタンを押下し試験問題画面に遷移")
	void test05() {

		// 「試験を開始する」ボタンをクリック
		WebElement stratExamBtn = webDriver.findElement(By.cssSelector("input[value = '試験を開始する']"));
		stratExamBtn.click();

		pageLoadTimeout(5);

		// 検証：試験問題画面への遷移
		assertEquals("第1問 【】",
				webDriver.findElement(By.className("panel-heading")).getText());
		assertEquals("ITリテラシー①", webDriver.findElement(By.tagName("h2")).getText());
		assertEquals("ITリテラシー① | LMS", webDriver.getTitle());

		// エビデンスの取得
		getEvidence(new Object() {
		});

		// 遷移後URLの確認
		String url = webDriver.getCurrentUrl();
		assertEquals("http://localhost:8080/lms/exam/question", url);
	}

	@Test
	@Order(6)
	@DisplayName("テスト06 未回答の状態で「確認画面へ進む」ボタンを押下し試験回答確認画面に遷移")
	void test06() {

		// エビデンスの取得①
		getEvidence(new Object() {
		}, "upper");

		scrollTo("4200");

		// エビデンスの取得②
		getEvidence(new Object() {
		}, "lower");

		// 「確認画面へ進む」ボタンをクリック
		WebElement answerCheckBtn = webDriver.findElement(By.cssSelector("input[value = '確認画面へ進む']"));
		answerCheckBtn.click();

		pageLoadTimeout(5);

		// 検証：試験回答確認画面への遷移
		assertEquals("* 回答が入力されていません。", webDriver.findElement(By.className("text-warning")).getText());
		assertEquals("ITリテラシー①  回答数：0／12問 ", webDriver.findElement(By.tagName("h2")).getText());
		assertEquals("ITリテラシー① | LMS", webDriver.getTitle());

		// エビデンスの取得③
		getEvidence(new Object() {
		}, "pageTransition");

		// 遷移後URLの確認
		String url = webDriver.getCurrentUrl();
		assertEquals("http://localhost:8080/lms/exam/answerCheck", url);
	}

	@Test
	@Order(7)
	@DisplayName("テスト07 「回答を送信する」ボタンを押下し試験結果画面に遷移")
	void test07() throws InterruptedException {

		Thread.sleep(1000);
		scrollTo("4200");

		// 「回答を送信する」ボタンをクリック
		WebElement answerCheckBtn = webDriver.findElement(By.id("sendButton"));
		answerCheckBtn.click();

		// ポップアップ
		webDriver.switchTo().alert().accept();

		pageLoadTimeout(5);

		// 検証：試験回答確認画面への遷移
		assertEquals("ITリテラシー① あなたのスコア：0.0点   正答数：0／12", webDriver.findElement(By.tagName("h2")).getText());
		assertEquals("ITリテラシー① | LMS", webDriver.getTitle());

		// エビデンスの取得
		getEvidence(new Object() {
		});

		// 遷移後URLの確認
		String url = webDriver.getCurrentUrl();
		assertEquals("http://localhost:8080/lms/exam/result", url);
	}

	@Test
	@Order(8)
	@DisplayName("テスト08 「戻る」ボタンを押下し試験開始画面に遷移後当該試験の結果が反映される")
	void test08() {

		scrollTo("5000");

		// エビデンスの取得①
		getEvidence(new Object() {
		}, "backBtn");

		// 「戻る」ボタンをクリック
		WebElement backBtn = webDriver.findElement(By.cssSelector("input[value = '戻る']"));
		backBtn.click();

		pageLoadTimeout(10);

		visibilityTimeout(By.xpath("//*[@id = 'main']/div/table[2]/tbody/tr[2]/td[4]"), 5);

		// 検証：試験問題画面への遷移
		WebElement resultExamDate = webDriver.findElement(By.xpath("//*[@id = 'main']/div/table[2]/tbody/tr[2]/td[4]"));
		assertEquals("ITリテラシー①", webDriver.findElement(By.tagName("h2")).getText());
		assertEquals("試験【ITリテラシー①】 | LMS", webDriver.getTitle());
		assertEquals("過去の試験結果", webDriver.findElement(By.tagName("h3")).getText());
		assertTrue(resultExamDate.isDisplayed());

		// エビデンスの取得②
		getEvidence(new Object() {
		}, "pageTransition");

		// 遷移後URLの確認
		String url = webDriver.getCurrentUrl();
		assertEquals("http://localhost:8080/lms/exam/start", url);
	}

}
