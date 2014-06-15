package br.com.dsoo.facebook.view.panels;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;
import java.util.List;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextPane;
import javax.swing.LayoutStyle.ComponentPlacement;

import br.com.dsoo.facebook.logic.Logger;
import br.com.dsoo.facebook.view.Alert;
import facebook4j.Category;
import facebook4j.Facebook;
import facebook4j.FacebookException;
import facebook4j.IdNameEntity;
import facebook4j.Like;
import facebook4j.PagableList;
import facebook4j.Paging;
import facebook4j.PictureSize;
import facebook4j.Post;

public class PostPanel extends JPanel implements ActionListener{

	private static final long serialVersionUID = 1L;
	
	private final Logger logger = new Logger("PostPanel");
	
	private final Post post;
	private Facebook fb;
	
	private Category from;
	private List<IdNameEntity> to;
	
	private boolean liked = false;
	private String postId, fromId;

	private JLabel fromName, toName, userPic;
	private final JButton btLike;
	private JTextPane txtPost;
	
	public PostPanel(Facebook fb, Post post) throws FacebookException{
		super();
		this.post = post;
		this.fb = fb;
		URL pic = null;
		
		from = post.getFrom();
		to = post.getTo();
		
		fromId = from.getId();
		postId = post.getId().split("_")[1];
		
		btLike = new JButton();
		
		pic = fb.getPictureURL(fromId, PictureSize.square);
		btLike.setText(isPostLiked() ? "Descurtir" : "Curtir");

		fromName = new JLabel(from.getName());
		fromName.setFont(new Font("Tahoma", Font.BOLD, 11));
		
		toName = new JLabel();

		if(to.size() > 1){
			toName.setText(" - ...");

			String tip = "";
			for(IdNameEntity person : to){
				tip += person.getName() + "\n";
			}

			toName.setToolTipText(tip.substring(0, tip.length() - 2));
		}else if(to.size() == 1){
			toName.setText(" - " + to.get(0).getName());
		}
		
		toName.setFont(new Font("Tahoma", Font.BOLD, 11));

		btLike.addActionListener(this);
		txtPost = new JTextPane();
		txtPost.setEditable(false);
		txtPost.setText(post.getMessage());
		
		if(pic != null){
			userPic = new JLabel(new ImageIcon(pic));
		}else{
			userPic = new JLabel(new ImageIcon("https://www.teenytinywebsites.com/s/facebook_f_icon_50.png"));
		}

		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup()
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(btLike)
						.addComponent(txtPost, GroupLayout.DEFAULT_SIZE, 220, Short.MAX_VALUE)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(userPic)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(fromName)
							.addComponent(toName)))
					.addContainerGap())
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup()
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(toName)
						.addComponent(fromName)
						.addComponent(userPic))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(txtPost, GroupLayout.PREFERRED_SIZE, 68, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(btLike)
					.addContainerGap())
		);
		setLayout(groupLayout);
		
		setBackground(Color.WHITE);
	}

	@Override
	public void actionPerformed(ActionEvent e){
		if(e.getSource() == btLike){
			try{
				if(!liked && fb.likePost(postId)){
					liked = true;
					btLike.setText("Descurtir");
				}else if(fb.unlikePost(postId)){
					liked = false;
					btLike.setText("Curtir");
				}
			}catch(FacebookException e1){
				Alert.showError(e1);
			}
		}
	}
	
	private boolean isPostLiked() throws FacebookException{
		PagableList<Like> likes = post.getLikes();
		String myId = fb.getMe().getId();
		
		Paging<Like> paging = likes.getPaging();
		while(likes != null){
			for(Like like : likes){
				if(like.getId().equals(myId)){
					return liked = true;
				}
			}
			
			if((paging = likes.getPaging()) != null){
				likes = fb.fetchNext(paging);
				continue;
			}
			
			break;
		}
		
		return liked = false;
	}
}
