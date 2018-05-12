package com.cdeledu.util.database.redis;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 把今天最好的表现当作明天最新的起点．．～
 *
 * Today the best performance as tomorrow newest starter!
 *
 * @类描述: Redis 操作接口
 * @创建者: 皇族灬战狼
 * @联系方式: duleilewuhen@sina.com
 * @创建时间: 2018年5月11日 上午8:50:11
 * @版本: V1.0
 * @since: JDK 1.7
 */
interface RedisBasicCommand {

	/************************************************************************/
	/******************* Redis 键(key) **************************************/
	/************************************************************************/

	/** 遍历所有的key */
	Set<String> getAllKeys();

	/** 查找当前数据库中所有符合给定模式 pattern 的 key */
	Set<String> getkeys(String pattern);

	/** 检查给定 key 是否存在 */
	Boolean exists(String key);

	/** 移除给定 key 的过期时间，使得 key 永不过期 */
	Long persist(String key);

	/** 返回 key 所储存的值的类型。 */
	String type(String key);

	/** 设置 key 的过期时间，以秒计。key 过期后将不再可用 */
	Long expire(String key, int seconds);

	/** 用于设置 key 的过期时间，以毫秒计。key 过期后将不再可用 */
	Long pexpire(String key, long milliseconds);

	/** 以秒为单位返回 key 的剩余过期时间 */
	Long ttl(String key);

	/** 以毫秒为单位返回 key 的剩余过期时间 */
	Long pttl(final String key);

	/** 修改 key 的名称 */
	String rename(String oldkey, String newkey);

	/** 在新的 key 不存在时修改 key 的名称 */
	Long renamenx(String oldkey, String newkey);

	/** 删除已存在的键。不存在的 key 会被忽略 */
	Long del(String... key);

	/************************************************************************/
	/******************* Redis 字符串(String) ********************************/
	/************************************************************************/

	/** 设置给定 key 的值。如果 key 已经存储其他值， SET 就覆写旧值，且无视类型 */
	String set(String key, String value);

	/** 在指定的 key 不存在时，为 key 设置指定的值 */
	Long setnx(String key, String value);

	/** 指定的 key 设置值及其过期时间。如果 key 已经存在， SETEX 命令将会替换旧的值 */
	String setex(String key, int seconds, String value);

	/** 以毫秒为单位设置 key 的生存时间 */
	String psetex(final String key, final long milliseconds, final String value);

	/** 获取指定 key 的值。如果 key 不存在，返回 nil 。如果key 储存的值不是字符串类型，返回一个错误 */
	String get(String key);

	/** 设置指定 key 的值，并返回 key 旧的值 */
	String getSex(String key, String value);

	/** 获取指定 key 所储存的字符串值的长度 */
	Long strLength(String key);

	/** 返回所有(一个或多个)给定 key 的值 */
	List<String> mget(String... keys);

	/** 获取存储在指定 key 中字符串的子字符串，截取范围由 start 和 end 两个偏移量决定 */
	String getrange(String key, long startOffset, long endOffset);

	/** 将 key 中储存的数字值增一,如果 key 不存在，那么 key 的值会先被初始化为 0 */
	Long incr(String key);

	/** 将 key 中储存的数字加上指定的增量值 */
	Long incrBy(String key, long integer);

	/** 为 key 中所储存的值加上指定的浮点数增量值 */
	Double incrByFloat(String key, double value);

	/** 将 key 中储存的数字值减一 */
	Long decr(String key);

	/** key 所储存的值减去指定的减量值 */
	Long decrBy(String key, long integer);

	/** 为指定的 key 追加值,如果 key 已经存在并且是一个字符串， 将 value 追加到 key 原来的值的末尾 */
	Long append(String key, String value);

	/************************************************************************/
	/******************* Redis 哈希(Hash) ************************************/
	/************************************************************************/

	/** 于删除哈希表 key 中的一个或多个指定字段，不存在的字段将被忽略 */
	Long hdel(String key, String... field);

	/** 获取哈希表中字段的数量 */
	Long hlen(String key);

	/** 获取哈希表中的所有字段名 */
	Set<String> hkeys(String key);

	/** 返回哈希表所有字段的值 */
	List<String> hvals(String key);

	/** 返回哈希表中指定字段的值 */
	String hget(String key, String field);

	/** 同时将多个 field-value (字段-值)对设置到哈希表中 */
	String hmset(String key, Map<String, String> hash);

	/** 返回哈希表中，一个或多个给定字段的值 */
	List<String> hmget(String key, String... fields);

	/** 返回哈希表中，所有的字段和值 */
	Map<String, String> hgetAll(String key);

	/** 查看哈希表的指定字段是否存在 */
	Boolean hexists(String key, String field);

	/** 哈希表中的字段赋值 */
	Long hset(String key, String field, String value);

	/************************************************************************/
	/******************* Redis 列表(List) ************************************/
	/************************************************************************/

	/** 移出并获取列表的第一个元素 */
	List<String> blpop(int timeout, String key);

	/** 移出并获取列表的最后一个元素 */
	List<String> brpop(int timeout, String key);

	/** 通过索引获取列表中的元素 */
	String lindex(String key, long index);

	/** 用于移除并返回列表的第一个元素 */
	String lpop(String key);

	/** 将一个或多个值插入到列表头部 */
	Long lpush(String key, String... string);

	/** 将一个或多个值插入到已存在的列表头部，列表不存在时操作无效 */
	Long lpushx(String key, String... value);

	/** 返回列表的长度。 如果列表 key 不存在，则 key 被解释为一个空列表，返回 0 */
	Long llen(String key);

	/** 返回列表中指定区间内的元素，区间以偏移量 START 和 END 指定 */
	List<String> lrange(String key, long start, long end);

	/** 根据参数 COUNT 的值，移除列表中与参数 VALUE 相等的元素 */
	Long lrem(String key, long count, String value);

	/** 对一个列表进行修剪(trim) */
	String ltrim(String key, long start, long end);

	/** 通过索引来设置元素的值 */
	String lset(String key, long index, String value);

	/** 移除并返回列表的最后一个元素 */
	String rpop(String key);

	/** 将一个或多个值插入到列表的尾部 */
	Long rpush(String key, String... string);

	/** 将一个或多个值插入到已存在的列表尾部(最右边)。如果列表不存在，操作无效 */
	Long rpushx(String key, String... string);

	/************************************************************************/
	/******************* Redis 集合(Set) *************************************/
	/************************************************************************/

	/** 将一个或多个成员元素加入到集合中 */
	Long sadd(String key, String... member);

	/** 返回给定集合之间的差集 */
	Set<String> sdiff(String... keys);

	/** 判断成员元素是否是集合的成员 */
	Boolean sismember(String key, String member);

	/** 返回集合中的所有的成员。 不存在的集合 key 被视为空集合。 */
	Set<String> smembers(String key);

	/** 返回给定所有给定集合的交集 */
	Set<String> sinter(String... keys);

	/** 移除并返回集合中的一个随机元素 */
	String spop(String key);

	Set<String> spop(String key, long count);

	/** 返回集合中的一个随机元素 */
	String srandmember(String key);

	List<String> srandmember(String key, int count);

	/** 用于移除集合中的一个或多个成员元素，不存在的成员元素会被忽略 */
	Long srem(String key, String... member);

	/** 返回集合中元素的数量 */
	Long scard(String key);

	/** 返回给定集合的并集。不存在的集合 key 被视为空集 */
	Set<String> sunion(String... keys);

	/************************************************************************/
	/******************* Redis 有序集合(sorted set) ************************/
	/************************************************************************/

	/** 将一个或多个成员元素及其分数值加入到有序集当中 */
	Long zadd(String key, double score, String member);

	/** 用于计算集合中元素的数量 */
	Long zcard(String key);

	/** 返回有序集中，成员的分数值。 如果成员元素不是有序集 key 的成员，或 key 不存在，返回 nil */
	Double zscore(String key, String member);

	/** 用于计算有序集合中指定分数区间的成员数量 */
	Long zcount(String key, double min, double max);

	/** 用于计算有序集合中指定分数区间的成员数量 */
	Long zcount(String key, String min, String max);

	/** 计算有序集合中指定字典区间内成员数量 */
	Long zlexcount(final String key, final String min, final String max);

	/** 返回有序集中，指定区间内的成员 */
	Set<String> zrange(String key, long start, long end);

	/** 通过字典区间返回有序集合的成员 */
	Set<String> zrangeByLex(final String key, final String min, final String max);

	Set<String> zrangeByLex(final String key, final String min, final String max, final int offset,
			final int count);

	/** 返回有序集合中指定分数区间的成员列表。有序集成员按分数值递增(从小到大)次序排列 */
	Set<String> zrangeByScore(String key, double min, double max);

	Set<String> zrangeByScore(String key, double min, double max, int offset, int count);

	/** 有序集中指定成员的排名。其中有序集成员按分数值递增(从小到大)顺序排列 */
	Long zrank(String key, String member);

	/** 命令返回有序集中成员的排名。其中有序集成员按分数值递减(从大到小)排序 */
	Long zrevrank(String key, String member);

	/** 移除有序集中的一个或多个成员，不存在的成员将被忽略 */
	Long zrem(String key, String... member);

	/** 用于移除有序集中，指定排名(rank)区间内的所有成员 */
	Long zremrangeByRank(String key, long start, long end);

	/** 用于移除有序集合中给定的字典区间的所有成员 */
	Long zremrangeByLex(final String key, final String min, final String max);

	/** 用于移除有序集中，指定分数（score）区间内的所有成员 */
	Long zremrangeByScore(String key, double start, double end);

	/** 用于移除有序集中，指定分数（score）区间内的所有成员 */
	Long zremrangeByScore(String key, String start, String end);

	/** 返回有序集中，指定区间内的成员,其中成员的位置按分数值递减(从大到小)来排列 */
	Set<String> zrevrange(String key, long start, long end);

	/** 返回有序集中指定分数区间内的所有的成员。有序集成员按分数值递减(从大到小)的次序排列 */
	Set<String> zrevrangeByScore(String key, double max, double min);

	Set<String> zrevrangeByScore(String key, double max, double min, int offset, int count);
}
