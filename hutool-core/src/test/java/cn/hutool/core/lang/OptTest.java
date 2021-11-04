package cn.hutool.core.lang;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import java.util.NoSuchElementException;

/**
 * {@link Opt}的单元测试
 *
 * @author VampireAchao
 */
public class OptTest {

	@Test
	public void ofBlankAbleTest() {
		// ofBlankAble相对于ofNullable考虑了字符串为空串的情况
		String hutool = Opt.ofBlankAble("").orElse("hutool");
		Assert.assertEquals("hutool", hutool);
	}

	@Test
	public void getTest() {
		// 和原版Optional有区别的是，get不会抛出NoSuchElementException
		// 如果想使用原版Optional中的get这样，获取一个一定不为空的值，则应该使用orElseThrow
		Object opt = Opt.ofNullable(null).get();
		Assert.assertNull(opt);
	}

	@Test
	public void isEmptyTest() {
		// 这是jdk11 Optional中的新函数，直接照搬了过来
		// 判断包裹内元素是否为空，注意并没有判断空字符串的情况
		boolean isEmpty = Opt.empty().isEmpty();
		Assert.assertTrue(isEmpty);
	}

	@Test
	@Ignore
	public void ifPresentOrElseTest() {
		// 这是jdk9中的新函数，直接照搬了过来
		// 存在就打印对应的值，不存在则用{@code System.err.println}打印另一句字符串
		Opt.ofNullable("Hello Hutool!").ifPresentOrElse(Console::log, () -> Console.error("Ops!Something is wrong!"));

		Opt.empty().ifPresentOrElse(Console::log, () -> Console.error("Ops!Something is wrong!"));
	}

	@Test
	public void peekTest() {
		User user = new User();
		// 相当于ifPresent的链式调用
		Opt.ofNullable("hutool").peek(user::setUsername).peek(user::setNickname);
		Assert.assertEquals("hutool", user.getNickname());
		Assert.assertEquals("hutool", user.getUsername());

		// 注意，传入的lambda中，对包裹内的元素执行赋值操作并不会影响到原来的元素
		String name = Opt.ofNullable("hutool").peek(username -> username = "123").peek(username -> username = "456").get();
		Assert.assertEquals("hutool", name);
	}

	@Test
	public void orTest() {
		// 这是jdk9 Optional中的新函数，直接照搬了过来
		// 给一个替代的Opt
		String str = Opt.<String>ofNullable(null).or(() -> Opt.ofNullable("Hello hutool!")).map(String::toUpperCase).orElseThrow();
		Assert.assertEquals("HELLO HUTOOL!", str);

		User user = User.builder().username("hutool").build();
		Opt<User> userOpt = Opt.of(user);
		// 获取昵称，获取不到则获取用户名
		String name = userOpt.map(User::getNickname).or(() -> userOpt.map(User::getUsername)).get();
		Assert.assertEquals("hutool", name);
	}

	@Test(expected = NoSuchElementException.class)
	public void orElseThrowTest() {
		// 获取一个不可能为空的值，否则抛出NoSuchElementException异常
		Object obj = Opt.ofNullable(null).orElseThrow();
		Assert.assertNull(obj);
	}

	@Test(expected = IllegalStateException.class)
	public void orElseThrowTest2() {
		// 获取一个不可能为空的值，否则抛出自定义异常
		Object assignException = Opt.ofNullable(null).orElseThrow(IllegalStateException::new);
		Assert.assertNull(assignException);
	}

	@Test(expected = IllegalStateException.class)
	public void orElseThrowTest3() {
		// 获取一个不可能为空的值，否则抛出带自定义消息的自定义异常
		Object exceptionWithMessage = Opt.empty().orElseThrow(IllegalStateException::new, "Ops!Something is wrong!");
		Assert.assertNull(exceptionWithMessage);
	}

	@Data
	@Builder
	@NoArgsConstructor
	@AllArgsConstructor
	static class User {
		private String username;
		private String nickname;
	}


}
