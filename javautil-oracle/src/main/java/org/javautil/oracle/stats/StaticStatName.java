package org.javautil.oracle.stats;

/**
 * This reflects the values of sys.v$stat_name as of 10.2.
 * 
 * This is intended only for use in unit testing, else use @ see StatNames.
 * 
 * @author jjs
 * 
 */
public class StaticStatName {
	public static StatNames getStatNames() {
		final StatNames sn = new StatNames();
		sn.add(new StatName(0, "logons cumulative", 1, 2666645286L));
		sn.add(new StatName(1, "logons current", 1, 3080465522L));
		sn.add(new StatName(2, "opened cursors cumulative", 1, 85052502L));
		sn.add(new StatName(3, "opened cursors current", 1, 2301954928L));
		sn.add(new StatName(4, "user commits", 1, 582481098L));
		sn.add(new StatName(5, "user rollbacks", 1, 3671147913L));
		sn.add(new StatName(6, "user calls", 1, 2882015696L));
		sn.add(new StatName(7, "recursive calls", 1, 2656001462L));
		sn.add(new StatName(8, "recursive cpu usage", 1, 4009879262L));
		sn.add(new StatName(9, "session logical reads", 1, 3143187968L));
		sn.add(new StatName(10, "session stored procedure space", 1, 895486831L));
		sn.add(new StatName(11, "CPU used when call started", 128, 572264820L));
		sn.add(new StatName(12, "CPU used by this session", 1, 24469293L));
		sn.add(new StatName(13, "DB time", 1, 3649082374L));
		sn.add(new StatName(14, "cluster wait time", 1, 2432034337L));
		sn.add(new StatName(15, "concurrency wait time", 1, 3868577743L));
		sn.add(new StatName(16, "application wait time", 1, 1099569955L));
		sn.add(new StatName(17, "user I/O wait time", 1, 3332107451L));
		sn.add(new StatName(18, "session connect time", 1, 1678473874L));
		sn.add(new StatName(19, "process last non-idle time", 128, 2049556447L));
		sn.add(new StatName(20, "session uga memory", 1, 1856888586L));
		sn.add(new StatName(21, "session uga memory max", 1, 3840343119L));
		sn.add(new StatName(22, "messages sent", 128, 2486045308L));
		sn.add(new StatName(23, "messages received", 128, 1396153242L));
		sn.add(new StatName(24, "background timeouts", 128, 961017212L));
		sn.add(new StatName(25, "session pga memory", 1, 4148600571L));
		sn.add(new StatName(26, "session pga memory max", 1, 507777907L));
		sn.add(new StatName(27, "enqueue timeouts", 4, 2425496215L));
		sn.add(new StatName(28, "enqueue waits", 4, 2307006529L));
		sn.add(new StatName(29, "enqueue deadlocks", 4, 472183780L));
		sn.add(new StatName(30, "enqueue requests", 4, 2440542518L));
		sn.add(new StatName(31, "enqueue conversions", 4, 57745673L));
		sn.add(new StatName(32, "enqueue releases", 4, 2553146675L));
		sn.add(new StatName(33, "global enqueue gets sync", 32, 1338475854L));
		sn.add(new StatName(34, "global enqueue gets async", 32, 2892637759L));
		sn.add(new StatName(35, "global enqueue get time", 32, 3744090840L));
		sn.add(new StatName(36, "global enqueue releases", 32, 1516404026L));
		sn.add(new StatName(37, "physical read total IO requests", 8,
				3343375620L));
		sn.add(new StatName(38, "physical read total multi block requests", 8,
				2007302071L));
		sn.add(new StatName(39, "physical read total bytes", 8, 2572010804L));
		sn.add(new StatName(40, "physical write total IO requests", 8,
				1315894329L));
		sn.add(new StatName(41, "physical write total multi block requests", 8,
				3540174003L));
		sn.add(new StatName(42, "physical write total bytes", 8, 2495644835L));
		sn.add(new StatName(43, "IPC CPU used by this session", 32, 4247517299L));
		sn.add(new StatName(44, "gcs messages sent", 32, 2765451804L));
		sn.add(new StatName(45, "ges messages sent", 32, 1145425433L));
		sn.add(new StatName(46, "global enqueue CPU used by this session", 32,
				3469911798L));
		sn.add(new StatName(47, "db block gets", 8, 1480709069L));
		sn.add(new StatName(48, "db block gets from cache", 8, 4017839461L));
		sn.add(new StatName(49, "db block gets direct", 8, 95128520L));
		sn.add(new StatName(50, "consistent gets", 8, 4162191256L));
		sn.add(new StatName(51, "consistent gets from cache", 8, 2839918855L));
		sn.add(new StatName(52, "consistent gets - examination", 8, 1701530557L));
		sn.add(new StatName(53, "consistent gets direct", 8, 420374750L));
		sn.add(new StatName(54, "physical reads", 8, 2263124246L));
		sn.add(new StatName(55, "physical reads cache", 8, 4171507801L));
		sn.add(new StatName(56, "physical reads direct", 8, 2589616721L));
		sn.add(new StatName(57, "physical read IO requests", 8, 789768877L));
		sn.add(new StatName(58, "physical read bytes", 8, 523531786L));
		sn.add(new StatName(59, "db block changes", 8, 916801489L));
		sn.add(new StatName(60, "consistent changes", 8, 3876379665L));
		sn.add(new StatName(61, "recovery blocks read", 8, 3327333035L));
		sn.add(new StatName(62, "physical writes", 8, 1190468109L));
		sn.add(new StatName(63, "physical writes direct", 8, 2699895516L));
		sn.add(new StatName(64, "physical writes from cache", 8, 163083034L));
		sn.add(new StatName(65, "physical write IO requests", 8, 2904164198L));
		sn.add(new StatName(66, "physical write bytes", 8, 3131337131L));
		sn.add(new StatName(67, "physical writes non checkpoint", 8,
				2602029796L));
		sn.add(new StatName(68, "summed dirty queue length", 8, 1683565474L));
		sn.add(new StatName(69, "DBWR checkpoint buffers written", 8,
				1208600358L));
		sn.add(new StatName(70, "DBWR thread checkpoint buffers written", 8,
				3905787588L));
		sn.add(new StatName(71, "DBWR tablespace checkpoint buffers written",
				8, 2649259263L));
		sn.add(new StatName(72,
				"DBWR parallel query checkpoint buffers written", 8,
				1768645316L));
		sn.add(new StatName(73, "DBWR object drop buffers written", 8,
				658143835L));
		sn.add(new StatName(74, "DBWR transaction table writes", 8, 2146120386L));
		sn.add(new StatName(75, "DBWR undo block writes", 8, 111270822L));
		sn.add(new StatName(76, "DBWR revisited being-written buffer", 8,
				2773697723L));
		sn.add(new StatName(77, "DBWR make free requests", 8, 1237874725L));
		sn.add(new StatName(78, "DBWR lru scans", 8, 2139101792L));
		sn.add(new StatName(79, "DBWR checkpoints", 8, 1732023165L));
		sn.add(new StatName(80, "DBWR fusion writes", 40, 2313150541L));
		sn.add(new StatName(81, "prefetch clients - keep", 8, 574469659L));
		sn.add(new StatName(82, "prefetch clients - recycle", 8, 4053781837L));
		sn.add(new StatName(83, "prefetch clients - default", 8, 1612053064L));
		sn.add(new StatName(84, "prefetch clients - 2k", 8, 396625644L));
		sn.add(new StatName(85, "prefetch clients - 4k", 8, 3252900252L));
		sn.add(new StatName(86, "prefetch clients - 8k", 8, 1402775714L));
		sn.add(new StatName(87, "prefetch clients - 16k", 8, 792304463L));
		sn.add(new StatName(88, "prefetch clients - 32k", 8, 2351177679L));
		sn.add(new StatName(89, "change write time", 8, 2838938797L));
		sn.add(new StatName(90, "redo synch writes", 8, 1439995281L));
		sn.add(new StatName(91, "redo synch time", 8, 4215815172L));
		sn.add(new StatName(92, "exchange deadlocks", 8, 14632483L));
		sn.add(new StatName(93, "free buffer requested", 8, 3411924934L));
		sn.add(new StatName(94, "dirty buffers inspected", 8, 1344569897L));
		sn.add(new StatName(95, "pinned buffers inspected", 8, 833456521L));
		sn.add(new StatName(96, "hot buffers moved to head of LRU", 8,
				2030212224L));
		sn.add(new StatName(97, "free buffer inspected", 8, 941676439L));
		sn.add(new StatName(98, "commit cleanout failures: write disabled", 8,
				2063165199L));
		sn.add(new StatName(99, "commit cleanout failures: block lost", 8,
				781179189L));
		sn.add(new StatName(100, "commit cleanout failures: cannot pin", 8,
				176423721L));
		sn.add(new StatName(101,
				"commit cleanout failures: hot backup in progress", 8,
				2500023621L));

		sn.add(new StatName(102,
				"commit cleanout failures: buffer being written", 8,
				1626141486L));
		sn.add(new StatName(103, "commit cleanout failures: callback failure ",
				8, 3086716748L));
		sn.add(new StatName(104, "commit cleanouts", 8, 2934869719L));
		sn.add(new StatName(105, "commit cleanouts successfully completed", 8,
				3554314084L));
		sn.add(new StatName(106, "recovery array reads", 8, 2617845348L));
		sn.add(new StatName(107, "recovery array read time", 8, 2996696667L));
		sn.add(new StatName(108, "CR blocks created", 8, 516801181L));
		sn.add(new StatName(109, "current blocks converted for CR", 8,
				3142330676L));
		sn.add(new StatName(110, "switch current to new buffer", 8, 1312802324L));
		sn.add(new StatName(111, "write clones created in foreground", 8,
				4237693830L));
		sn.add(new StatName(112, "write clones created in background", 8,
				2988965544L));
		sn.add(new StatName(113, "write clones created for recovery", 8,
				4053425429L));
		sn.add(new StatName(114, "physical reads cache prefetch", 8, 297908839L));
		sn.add(new StatName(115, "physical reads prefetch warmup", 8,
				3102888545L));
		sn.add(new StatName(116, "prefetched blocks aged out before use", 8,
				1457461390L));
		sn.add(new StatName(117, "prefetch warmup blocks aged out before use",
				8, 3250306695L));
		sn.add(new StatName(118,
				"prefetch warmup blocks flushed out before use", 8, 2218846789L));
		sn.add(new StatName(119, "physical reads retry corrupt", 8, 531193461L));
		sn.add(new StatName(120, "physical reads direct (lob)", 8, 2564935310L));
		sn.add(new StatName(121, "physical reads direct temporary tablespace",
				8, 2663793346L));
		sn.add(new StatName(122, "physical writes direct (lob)", 8, 3308932835L));
		sn.add(new StatName(123, "physical writes direct temporary tablespace",
				8, 996415569L));
		sn.add(new StatName(124, "cold recycle reads", 8, 657271764L));
		sn.add(new StatName(125, "shared hash latch upgrades - no wait", 8,
				2488550671L));
		sn.add(new StatName(126, "shared hash latch upgrades - wait", 8,
				3380476499L));
		sn.add(new StatName(127, "physical reads for flashback new", 8,
				473165409L));
		sn.add(new StatName(128, "calls to kcmgcs", 128, 2775936802L));
		sn.add(new StatName(129, "calls to kcmgrs", 128, 4210206824L));
		sn.add(new StatName(130, "calls to kcmgas", 128, 4072914524L));
		sn.add(new StatName(131, "calls to get snapshot scn: kcmgss", 32,
				3128650623L));
		sn.add(new StatName(132, "redo blocks read for recovery", 2,
				2679943069L));
		sn.add(new StatName(133, "redo entries", 2, 3488821837L));
		sn.add(new StatName(134, "redo size", 2, 1236385760L));
		sn.add(new StatName(135, "redo buffer allocation retries", 2,
				1446958922L));
		sn.add(new StatName(136, "redo wastage", 2, 3462806146L));
		sn.add(new StatName(137, "redo writer latching time", 2, 2166056472L));
		sn.add(new StatName(138, "redo writes", 2, 1948353376L));
		sn.add(new StatName(139, "redo blocks written", 2, 2391431605L));
		sn.add(new StatName(140, "redo write time", 2, 3094453259L));
		sn.add(new StatName(141, "redo log space requests", 2, 1985754937L));
		sn.add(new StatName(142, "redo log space wait time", 2, 252430928L));
		sn.add(new StatName(143, "redo log switch interrupts", 2, 674283274L));
		sn.add(new StatName(144, "redo ordering marks", 2, 2104561012L));
		sn.add(new StatName(145, "redo subscn max counts", 2, 449106517L));
		sn.add(new StatName(146, "gc cr blocks served", 40, 1075941831L));
		sn.add(new StatName(147, "gc cr block build time", 40, 467525985L));
		sn.add(new StatName(148, "gc cr block flush time", 40, 552470873L));
		sn.add(new StatName(149, "gc cr block send time", 40, 2395315974L));
		sn.add(new StatName(150, "gc current blocks served", 40, 42062110L));
		sn.add(new StatName(151, "gc current block pin time", 40, 324533635L));
		sn.add(new StatName(152, "gc current block flush time", 40, 4091964965L));
		sn.add(new StatName(153, "gc current block send time", 40, 2750158241L));
		sn.add(new StatName(154, "gc cr blocks received", 40, 2877738702L));
		sn.add(new StatName(155, "gc cr block receive time", 40, 1759426133L));
		sn.add(new StatName(156, "gc current blocks received", 40, 326482564L));
		sn.add(new StatName(157, "gc current block receive time", 40,
				1388758753L));
		sn.add(new StatName(158, "gc blocks lost", 40, 500461751L));
		sn.add(new StatName(159, "gc claim blocks lost", 40, 1966230523L));
		sn.add(new StatName(160, "gc blocks corrupt", 40, 2288508531L));
		sn.add(new StatName(161, "gc CPU used by this session", 40, 4093034494L));
		sn.add(new StatName(162, "total number of slots", 8, 2223809671L));
		sn.add(new StatName(163, "instance recovery database freeze count", 32,
				3674920298L));
		sn.add(new StatName(164, "background checkpoints started", 8,
				3048007007L));
		sn.add(new StatName(165, "background checkpoints completed", 8,
				2450922463L));
		sn.add(new StatName(166, "number of map operations", 8, 337177656L));
		sn.add(new StatName(167, "number of map misses", 8, 3618703100L));
		sn.add(new StatName(168, "flashback log writes", 2, 3123176560L));
		sn.add(new StatName(169, "serializable aborts", 1, 1793250745L));
		sn.add(new StatName(170, "transaction lock foreground requests", 128,
				185785666L));
		sn.add(new StatName(171, "transaction lock foreground wait time", 128,
				4033661655L));
		sn.add(new StatName(172, "transaction lock background gets", 128,
				3977359732L));
		sn.add(new StatName(173, "transaction lock background get time", 128,
				3492664117L));
		sn.add(new StatName(174, "undo change vector size", 128, 1465971540L));
		sn.add(new StatName(175,
				"transaction tables consistent reads - undo records applied",
				128, 1054055970L));

		sn.add(new StatName(176,
				"transaction tables consistent read rollbacks", 128,
				1396293204L));
		sn.add(new StatName(177,
				"data blocks consistent reads - undo records applied", 128,
				2915445793L));
		sn.add(new StatName(178, "no work - consistent read gets", 128,
				2814375799L));
		sn.add(new StatName(179, "cleanouts only - consistent read gets", 128,
				2904875805L));
		sn.add(new StatName(180, "rollbacks only - consistent read gets", 128,
				3133064500L));
		sn.add(new StatName(181,
				"cleanouts and rollbacks - consistent read gets", 128,
				1083903169L));

		sn.add(new StatName(182, "RowCR attempts", 128, 2297882326L));
		sn.add(new StatName(183, "RowCR hits", 128, 3455911385L));
		sn.add(new StatName(184, "RowCR - row contention", 128, 1175104080L));
		sn.add(new StatName(185, "RowCR - resume", 128, 2583359573L));
		sn.add(new StatName(186, "rollback changes - undo records applied",
				128, 3616249137L));
		sn.add(new StatName(187, "transaction rollbacks", 128, 3988650402L));
		sn.add(new StatName(188,
				"immediate (CURRENT) block cleanout applications", 128,
				2668233561L));

		sn.add(new StatName(189, "immediate (CR) block cleanout applications",
				128, 3318186802L));
		sn.add(new StatName(190,
				"deferred (CURRENT) block cleanout applications", 128,
				3388736327L));

		sn.add(new StatName(191, "commit txn count during cleanout", 128,
				1405359431L));
		sn.add(new StatName(192, "active txn count during cleanout", 128,
				3378035478L));
		sn.add(new StatName(193, "cleanout - number of ktugct calls", 128,
				2577316246L));
		sn.add(new StatName(194, "immediate CR cleanouts (index blocks)", 128,
				79013953L));
		sn.add(new StatName(195, "deferred CUR cleanouts (index blocks)", 128,
				3827443394L));
		sn.add(new StatName(196, "Commit SCN cached", 128, 2460709063L));
		sn.add(new StatName(197, "Cached Commit SCN referenced", 128,
				2374582560L));
		sn.add(new StatName(198, "auto extends on undo tablespace", 128,
				3140365462L));
		sn.add(new StatName(199, "drop segment calls in space pressure", 128,
				1500291109L));
		sn.add(new StatName(200, "total number of undo segments dropped", 128,
				3623640507L));
		sn.add(new StatName(201, "doubling up with imu segment", 128,
				723921798L));
		sn.add(new StatName(202, "tune down retentions in space pressure", 128,
				142917891L));
		sn.add(new StatName(203, "steps of tune down ret. in space pressure",
				128, 3986010614L));
		sn.add(new StatName(204, "space was found by tune down", 128,
				342698422L));
		sn.add(new StatName(205, "space was not found by tune down", 128,
				849561940L));
		sn.add(new StatName(206, "commit batch/immediate requested", 1,
				640321576L));
		sn.add(new StatName(207, "commit batch requested", 1, 2338940602L));
		sn.add(new StatName(208, "commit immediate requested", 1, 796663742L));
		sn.add(new StatName(209, "commit batch/immediate performed", 1,
				3711642721L));
		sn.add(new StatName(210, "commit batch performed", 1, 1391516579L));
		sn.add(new StatName(211, "commit immediate performed", 1, 3494177237L));
		sn.add(new StatName(212, "commit wait/nowait requested", 1, 3800399491L));
		sn.add(new StatName(213, "commit nowait requested", 1, 3697788123L));
		sn.add(new StatName(214, "commit wait requested", 1, 2843300285L));
		sn.add(new StatName(215, "commit wait/nowait performed", 1, 1541239326L));
		sn.add(new StatName(216, "commit nowait performed", 1, 485928497L));
		sn.add(new StatName(217, "commit wait performed", 1, 8047752L));
		sn.add(new StatName(218, "global undo segment hints helped", 128,
				1791494885L));
		sn.add(new StatName(219, "global undo segment hints were stale", 128,
				3309048233L));
		sn.add(new StatName(220, "local undo segment hints helped", 128,
				1047863356L));
		sn.add(new StatName(221, "local undo segment hints were stale", 128,
				2070200837L));
		sn.add(new StatName(222, "undo segment header was pinned", 128,
				248401831L));
		sn.add(new StatName(223, "total number of times SMON posted", 128,
				399827284L));
		sn.add(new StatName(224, "SMON posted for undo segment recovery", 128,
				2117898593L));
		sn.add(new StatName(225,
				"SMON posted for txn recovery for other instances", 128,
				1263842513L));
		sn.add(new StatName(226, "SMON posted for instance recovery", 128,
				4003178738L));
		sn.add(new StatName(227, "SMON posted for undo segment shrink", 128,
				579492169L));
		sn.add(new StatName(228, "SMON posted for dropping temp segment", 128,
				3600495721L));
		sn.add(new StatName(229,
				"SMON posted for supplemental logging cleanup", 128,
				1356155743L));
		sn.add(new StatName(230, "IMU commits", 128, 1914489094L));
		sn.add(new StatName(231, "IMU Flushes", 128, 2099506212L));
		sn.add(new StatName(232, "IMU contention", 128, 2909373607L));
		sn.add(new StatName(233, "IMU recursive-transaction flush", 128,
				2591100633L));
		sn.add(new StatName(234, "IMU undo retention flush", 128, 2087226422L));
		sn.add(new StatName(235, "IMU ktichg flush", 128, 1206609541L));
		sn.add(new StatName(236, "IMU bind flushes", 128, 2756376339L));
		sn.add(new StatName(237, "IMU mbu flush", 128, 3723686946L));
		sn.add(new StatName(238, "IMU pool not allocated", 128, 659017805L));
		sn.add(new StatName(239, "IMU CR rollbacks", 128, 2225124543L));
		sn.add(new StatName(240, "IMU undo allocation size", 128, 244193920L));
		sn.add(new StatName(241, "IMU Redo allocation size", 128, 3945654623L));
		sn.add(new StatName(242, "IMU- failed to get a private strand", 128,
				2412863545L));
		sn.add(new StatName(243, "Misses for writing mapping", 128, 2725329757L));
		sn.add(new StatName(244, "table scans (short tables)", 64, 345141973L));
		sn.add(new StatName(245, "table scans (long tables)", 64, 1042655239L));
		sn.add(new StatName(246, "table scans (rowid ranges)", 64, 1303412790L));
		sn.add(new StatName(247, "table scans (cache partitions)", 64,
				133545664L));
		sn.add(new StatName(248, "table scans (direct read)", 64, 3355423586L));
		sn.add(new StatName(249, "table scan rows gotten", 64, 1400824662L));
		sn.add(new StatName(250, "table scan blocks gotten", 64, 3741388076L));
		sn.add(new StatName(251, "table fetch by rowid", 64, 681815839L));
		sn.add(new StatName(252, "table fetch continued row", 64, 1413702393L));
		sn.add(new StatName(253, "cluster key scans", 64, 3566804345L));
		sn.add(new StatName(254, "cluster key scan block gets", 64, 2006808371L));
		sn.add(new StatName(255, "rows fetched via callback", 64, 3299365556L));
		sn.add(new StatName(256, "queue update without cp update", 128,
				528828405L));
		sn.add(new StatName(257, "leaf node splits", 128, 1417124053L));
		sn.add(new StatName(258, "leaf node 90-10 splits", 128, 1285138251L));
		sn.add(new StatName(259, "branch node splits", 128, 399027615L));
		sn.add(new StatName(260, "native hash arithmetic execute", 64,
				321852432L));
		sn.add(new StatName(261, "native hash arithmetic fail", 64, 1937851790L));
		sn.add(new StatName(262, "lob reads", 8, 3626914479L));
		sn.add(new StatName(263, "lob writes", 8, 2682192071L));
		sn.add(new StatName(264, "lob writes unaligned", 8, 4224694283L));
		sn.add(new StatName(265, "index fast full scans (full)", 64, 12081473L));
		sn.add(new StatName(266, "index fast full scans (rowid ranges)", 64,
				1555464060L));
		sn.add(new StatName(267, "index fast full scans (direct read)", 64,
				1635662180L));
		sn.add(new StatName(268, "index fetch by key", 128, 2808364230L));
		sn.add(new StatName(269, "index scans kdiixs1", 128, 3589793292L));
		sn.add(new StatName(270, "queue splits", 128, 1168925905L));
		sn.add(new StatName(271, "queue flush", 128, 3150060076L));
		sn.add(new StatName(272, "queue position update", 128, 2568542507L));
		sn.add(new StatName(273, "queue single row", 128, 2713404457L));
		sn.add(new StatName(274, "queue ocp pages", 128, 3899522644L));
		sn.add(new StatName(275, "queue qno pages", 128, 2998782417L));
		sn.add(new StatName(276, "heap block compress", 64, 2326757257L));
		sn.add(new StatName(277, "sql area purged", 64, 2763387941L));
		sn.add(new StatName(278, "sql area aged", 64, 3156625321L));
		sn.add(new StatName(279, "session cursor cache hits", 64, 3678609077L));
		sn.add(new StatName(280, "session cursor cache count", 64, 568260813L));
		sn.add(new StatName(281, "java call heap total size", 1, 1649812842L));
		sn.add(new StatName(282, "java call heap total size max", 1, 580075922L));
		sn.add(new StatName(283, "java call heap used size", 1, 187277223L));
		sn.add(new StatName(284, "java call heap used size max", 1, 625960724L));
		sn.add(new StatName(285, "java call heap live size", 1, 2431389341L));
		sn.add(new StatName(286, "java call heap live size max", 1, 1298432410L));
		sn.add(new StatName(287, "java call heap object count", 1, 3472185722L));
		sn.add(new StatName(288, "java call heap object count max", 1,
				1862536587L));
		sn.add(new StatName(289, "java call heap live object count", 1,
				632577394L));
		sn.add(new StatName(290, "java call heap live object count max", 1,
				2964139762L));
		sn.add(new StatName(291, "java call heap gc count", 1, 2284697213L));
		sn.add(new StatName(292, "java call heap collected count", 1,
				2510746206L));
		sn.add(new StatName(293, "java call heap collected bytes", 1,
				3585877838L));
		sn.add(new StatName(294, "java session heap used size", 1, 1661140639L));
		sn.add(new StatName(295, "java session heap used size max", 1,
				1726629032L));
		sn.add(new StatName(296, "java session heap live size", 1, 2257986540L));
		sn.add(new StatName(297, "java session heap live size max", 1,
				1960804148L));
		sn.add(new StatName(298, "java session heap object count", 1,
				4072938679L));
		sn.add(new StatName(299, "java session heap object count max", 1,
				469647992L));
		sn.add(new StatName(300, "java session heap live object count", 1,
				225051972L));
		sn.add(new StatName(301, "java session heap live object count max", 1,
				3755633366L));
		sn.add(new StatName(302, "java session heap gc count", 1, 3363147808L));
		sn.add(new StatName(303, "java session heap collected count", 1,
				3795934475L));
		sn.add(new StatName(304, "java session heap collected bytes", 1,
				3976434298L));
		sn.add(new StatName(305, "cursor authentications", 128, 4069981174L));
		sn.add(new StatName(306, "queries parallelized", 32, 3315614405L));
		sn.add(new StatName(307, "DML statements parallelized", 32, 3791923855L));
		sn.add(new StatName(308, "DDL statements parallelized", 32, 4115134636L));
		sn.add(new StatName(309, "DFO trees parallelized", 32, 2753131890L));
		sn.add(new StatName(310, "Parallel operations not downgraded", 32,
				2410168744L));
		sn.add(new StatName(311, "Parallel operations downgraded to serial",
				32, 4286011915L));
		sn.add(new StatName(312, "Parallel operations downgraded 75 to 99 pct",
				32, 322858058L));
		sn.add(new StatName(313, "Parallel operations downgraded 50 to 75 pct",
				32, 3281923117L));
		sn.add(new StatName(314, "Parallel operations downgraded 25 to 50 pct",
				32, 3330456527L));
		sn.add(new StatName(315, "Parallel operations downgraded 1 to 25 pct",
				32, 3721245209L));
		sn.add(new StatName(316, "PX local messages sent", 32, 506655048L));
		sn.add(new StatName(317, "PX local messages recv'd", 32, 1437798390L));
		sn.add(new StatName(318, "PX remote messages sent", 32, 2567415317L));
		sn.add(new StatName(319, "PX remote messages recv'd", 32, 2537140420L));
		sn.add(new StatName(320, "buffer is pinned count", 72, 1300470380L));
		sn.add(new StatName(321, "buffer is not pinned count", 72, 2670431739L));
		sn.add(new StatName(322, "no buffer to keep pinned count", 72,
				3528454686L));
		sn.add(new StatName(323, "table lookup prefetch client count", 8,
				1926456670L));
		sn.add(new StatName(324, "workarea memory allocated", 64, 2433935387L));
		sn.add(new StatName(325, "workarea executions - optimal", 64,
				3211650785L));
		sn.add(new StatName(326, "workarea executions - onepass", 64,
				798730793L));
		sn.add(new StatName(327, "workarea executions - multipass", 64,
				3804491469L));
		sn.add(new StatName(328, "parse time cpu", 64, 206905303L));
		sn.add(new StatName(329, "parse time elapsed", 64, 1431595225L));
		sn.add(new StatName(330, "parse count (total)", 64, 63887964L));
		sn.add(new StatName(331, "parse count (hard)", 64, 143509059L));
		sn.add(new StatName(332, "parse count (failures)", 64, 1118776443L));
		sn.add(new StatName(333, "frame signature mismatch", 64, 959148951L));
		sn.add(new StatName(334, "execute count", 64, 2453370665L));
		sn.add(new StatName(335, "bytes sent via SQL*Net to client", 1,
				2967415760L));
		sn.add(new StatName(336, "bytes received via SQL*Net from client", 1,
				161936656L));
		sn.add(new StatName(337, "SQL*Net roundtrips to/from client", 1,
				159846526L));
		sn.add(new StatName(338, "bytes sent via SQL*Net to dblink", 1,
				1622773540L));
		sn.add(new StatName(339, "bytes received via SQL*Net from dblink", 1,
				1983609624L));
		sn.add(new StatName(340, "SQL*Net roundtrips to/from dblink", 1,
				2420448902L));
		sn.add(new StatName(341, "sorts (memory)", 64, 2091983730L));
		sn.add(new StatName(342, "sorts (disk)", 64, 2533123502L));
		sn.add(new StatName(343, "sorts (rows)", 64, 3757672740L));
		sn.add(new StatName(344, "OTC commit optimization attempts", 128,
				2480822770L));
		sn.add(new StatName(345, "OTC commit optimization hits", 128,
				4064864054L));
		sn.add(new StatName(346, "OTC commit optimization failure - setup",
				128, 3633344886L));
		sn.add(new StatName(347, "OS User time used", 16, 3628421385L));
		sn.add(new StatName(348, "OS System time used", 16, 1159355779L));
		sn.add(new StatName(349, "OS Maximum resident set size", 16, 838789841L));
		sn.add(new StatName(350, "OS Integral shared text size", 16,
				2409964096L));
		sn.add(new StatName(351, "OS Integral unshared data size", 16,
				56092924L));
		sn.add(new StatName(352, "OS Integral unshared stack size", 16,
				2957272649L));
		sn.add(new StatName(353, "OS Page reclaims", 16, 800986673L));
		sn.add(new StatName(354, "OS Page faults", 16, 3353033775L));
		sn.add(new StatName(355, "OS Swaps", 16, 1982803996L));
		sn.add(new StatName(356, "OS Block input operations", 16, 953749291L));
		sn.add(new StatName(357, "OS Block output operations", 16, 756247347L));
		sn.add(new StatName(358, "OS Socket messages sent", 16, 1887989827L));
		sn.add(new StatName(359, "OS Socket messages received", 16, 2122589827L));
		sn.add(new StatName(360, "OS Signals received", 16, 210375991L));
		sn.add(new StatName(361, "OS Voluntary context switches", 16,
				2422402766L));
		sn.add(new StatName(362, "OS Involuntary context switches", 16,
				3316937952L));
		return sn;

	}
}
