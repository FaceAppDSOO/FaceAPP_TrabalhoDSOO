package br.com.dsoo.facebook.view.forms.panels;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextPane;
import javax.swing.LayoutStyle.ComponentPlacement;

import br.com.dsoo.facebook.view.Alert;
import facebook4j.Facebook;
import facebook4j.FacebookException;
import facebook4j.FeedTargetingParameter.Gender;
import facebook4j.Like;
import facebook4j.PagableList;
import facebook4j.Paging;
import facebook4j.PictureSize;
import facebook4j.Post;
import facebook4j.User;

public class PostPanel extends JPanel implements ActionListener{

	private static final long serialVersionUID = 1L;
	
	private final Post post;
	private Facebook fb;
	private User user;
	
	private boolean liked = false;
	private String postId, userId;

	private JLabel userName, userPic;
	private final JButton btLike;
	private JTextPane txtPost;
	
	public PostPanel(Facebook fb, Post post){
		super();
		this.post = post;
		this.fb = fb;
		URL pic = null;
		String[] ids = post.getId().split("_");
		userId = ids[0];
		postId = ids[1];
		
		btLike = new JButton();
		
		try{
			user = fb.getUser(userId);
			pic = fb.getPictureURL(userId, PictureSize.square);
			btLike.setText(isPostLiked() ? "Descurtir" : "Curtir");
		}catch(FacebookException e){
			Alert.showError(e);
		}

		userName = new JLabel(user.getName());
		userName.setFont(new Font("Tahoma", Font.BOLD, 11));		
		btLike.addActionListener(this);
		txtPost = new JTextPane();
		txtPost.setEditable(false);
		txtPost.setText(post.getMessage());
		
		if(pic != null){
			userPic = new JLabel(new ImageIcon(pic));
		}else if(user.getGender() == Gender.Male.name()){
			userPic = new JLabel(new ImageIcon("http://www.theipadguide.com/images/facebook50x50.jpg"));
		}else{
			userPic = new JLabel(new ImageIcon("http://www.runningguru.com/images/DefaultFemale.gif"));
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
							.addComponent(userName)))
					.addContainerGap())
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup()
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(userName)
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
				if(fb.likePost(postId)){
					liked = !liked;
					btLike.setText(liked ? "Descurtir" : "Curtir");
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
