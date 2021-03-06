package cn.anthony.luguhu.wp.handler;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.anthony.luguhu.domain.Asset;
import cn.anthony.luguhu.domain.Tag;
import cn.anthony.luguhu.domain.WxUser;
import cn.anthony.luguhu.repository.TagRepository;
import cn.anthony.luguhu.repository.WxUserRepository;
import cn.anthony.luguhu.service.AssetService;
import cn.anthony.luguhu.util.Constant;
import cn.anthony.luguhu.util.JsonUtils;
import cn.anthony.util.StringTools;
import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.common.session.WxSessionManager;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;

/**
 * 
 * @author Binary Wang
 *
 */
@Component
public class MsgHandler extends AbstractHandler {
	@Resource
	Constant constant;
	@Autowired
	private WxUserRepository userRepo;
	@Autowired
	private AssetService assetServ;
	@Autowired
	private TagRepository tagRepo;

	@Override
	public WxMpXmlOutMessage handle(WxMpXmlMessage wxMessage, Map<String, Object> context, WxMpService wxService,
			WxSessionManager sessionManager) throws WxErrorException {
		if (!wxMessage.getMsgType().equals(WxConsts.XmlMsgType.EVENT)) {
			// TODO 可以选择将消息保存到本地
		}
		// 当用户输入关键词如“你好”，“客服”等，并且有客服在线时，把消息转发给在线客服
		try {
			if (StringUtils.startsWithAny(wxMessage.getContent(), "你好", "客服")
					&& wxService.getKefuService().kfOnlineList().getKfOnlineList().size() > 0) {
				return WxMpXmlOutMessage.TRANSFER_CUSTOMER_SERVICE().fromUser(wxMessage.getToUser()).toUser(wxMessage.getFromUser())
						.build();
			}
		} catch (WxErrorException e) {
			e.printStackTrace();
		}
		// 如果是特定用户，则保留其消息
		WxUser wxUser = userRepo.findByOpenId(wxMessage.getFromUser());
		if (wxUser != null && wxUser.getLevel()!=null&&wxUser.getLevel() >= 5) {
			String msgType = wxMessage.getMsgType();
			Asset asset = new Asset();
			asset.setCreateFrom("WECHAT");
			asset.setType(msgType);
			asset.setWxUser(wxUser);
			asset.setOpen(false);
			List<Tag> tags = null;
			switch (msgType) {
			case WxConsts.XmlMsgType.IMAGE:
				String mediaId = wxMessage.getMediaId();
				File f = wxService.getMaterialService().mediaDownload(mediaId);
				String fileName = StringTools.createFileNameWithYM(f.getName());
				try {
					FileUtils.moveFile(f, new File(constant.getUploadAbsoluteDir() + Constant.FILE_SEPA + fileName));
				} catch (IOException e) {
					e.printStackTrace();
				}
				asset.setLocation(fileName);
				asset.setType(WxConsts.XmlMsgType.IMAGE);
				asset.setSourceName(f.getName());
				asset.setTitle(FilenameUtils.getBaseName(f.getName()));
				break;
			case WxConsts.XmlMsgType.TEXT:
				// 从文本里读取标签
				String content = wxMessage.getContent();
				asset.setSourceName(content);
				Set<String> set = new HashSet<String>();
				set.add("＠");set.add("@");
				List<String> labelList = StringTools.extract(new StringBuilder(content), set, " ");
				tags = getOrCreateTags(labelList);
				break;
			}
			if(tags==null||tags.size()==0) {
				tags = getLastTag(wxUser);
			}
			asset.setTags(tags);
			assetServ.create(asset);
		}
		String content = "收到信息内容：" + JsonUtils.toJson(wxMessage);
		//return new TextBuilder().build(content, wxMessage, wxService);
		return null;
	}

	private List<Tag> getLastTag(WxUser wxUser) {
		List<Asset> l = assetServ.findByWxUserHaveTag(wxUser);
		List<Tag> tags = new ArrayList<Tag>();
		if(l!=null&&l.size()>0)
			//这样做是为了避免这个异常：Found shared references to a collection
			tags.addAll(l.get(0).getTags());
		return tags;
	}

	private List<Tag> getOrCreateTags(List<String> labelList) {
		List<Tag> l = new ArrayList<Tag>();
		for (String s : labelList) {
			Tag t = tagRepo.findByLabel(s);
			if (t != null)
				l.add(t);
			else
				l.add(tagRepo.save(new Tag(s)));
		}
		return l;
	}
}
