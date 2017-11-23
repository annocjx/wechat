<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<html>
<head>
<%@ include file="/WEB-INF/webviews/common/common.jsp"%>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>HTML5 多媒体播放器</title>
<link rel="stylesheet" href="${_currConText }/plug-in/assets/css/bootstrap.min.css"/>
<link rel="stylesheet" href="${_currConText }/static/css/echarts/style.css" />
<script type="text/javascript" src="${_currConText }/plug-in/jquery/jquery-1.10.2.min.js"></script>
</head>
<body>
<body class="gray-bg">
	<div class="wrapper wrapper-content animated fadeInRight">
		<div class="row">
			<div class="col-sm-12">
				<div class="ibox">
					<div class="ibox-title">
						<h5>
							HTML5 多媒体播放器 <a href="https://plyr.io/" target="_blank">https://plyr.io/</a>
						</h5>
					</div>
					<div class="ibox-content">
						<p>Plyr 是一个使用 HTML5 开发的基于浏览器上的多媒体播放器。支持自定义的播放控制和&nbsp;WebVTT
							字幕。</p>
						<p>特性：</p>
						<ul>
							<li>
								<p>
									<strong>可访问性</strong> - 完全支持字幕和屏幕阅读器
								</p>
							</li>
							<li>
								<p>
									<strong>轻量级</strong> - 压缩后只有 4.8Kb
								</p>
							</li>
							<li>
								<p>
									<strong>可定制</strong> - 外观可以根据需要进行调整
								</p>
							</li>
							<li>
								<p>
									<strong>语义化</strong> - 使用 HTML5 的输入框进行音量和进度的调整
								</p>
							</li>
							<li>
								<p>
									<strong>快速响应</strong> - 就像你预期的那样
								</p>
							</li>
							<li>
								<p>
									<strong>音频和视频</strong>- 支持视频和纯音频
								</p>
							</li>
							<li>
								<p>
									<strong>API</strong>&nbsp;- 易用 API
								</p>
							</li>
							<li>
								<p>
									<strong>向下兼容</strong> - 如果浏览器不支持，则自动使用内建播放器
								</p>
							</li>
							<li>
								<p>
									<strong>全屏支持</strong> -支持原生全屏和退出全屏
								</p>
							</li>
							<li>
								<p>
									<strong>无依赖</strong> - 使用原生 JS 编写，不需要jQuery
								</p>
							</li>
						</ul>
					</div>
				</div>
			</div>
				<div class="col-sm-4">
				<div class="ibox float-e-margins">
					<div class="ibox-title">
						<h5>直播房间列表</h5>
					</div>
					<div class="ibox-content">
						<ul class="list-group">
							<li class="list-group-item">
								免费域名注册<span class="pull-right label label-default">1</span>
							</li>
							<li class="list-group-item">
								免费 Window空间托管<span class="pull-right label label-primary">2</span>
							</li>
							<li class="list-group-item">
								图像的数量<span class="pull-right label label-success">3</span>
							</li>
							<li class="list-group-item">
								24*7 支持<span class="pull-right label label-info">4</span>
							</li>
							<li class="list-group-item">
								每年更新成本<span class="pull-right label label-warning">5</span>
							</li>
						</ul>
					</div>
				</div>
			</div>
			<div class="col-sm-4">
				<div class="ibox float-e-margins">
					<div class="ibox-title">
						<h5>音频播放</h5>
					</div>
					<div class="ibox-content">
						<div class="player">
							<audio controls src="http://music.baidu.com/cms/app/muplayer/test_mp3/1.mp3">
							</audio>
						</div>
					</div>
				</div>
			</div>
			<div class="col-sm-8">
				<div class="ibox float-e-margins">
					<div class="ibox-title">
						<h5>视频播放</h5>
					</div>
					<div class="ibox-content">
						<div class="player">
							<video controls autoplay src="https://vplscdn.videojj.com/video/zongyi.mp4" loop>
							</video>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>