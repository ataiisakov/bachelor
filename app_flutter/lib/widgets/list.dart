
import 'package:app_flutter/screens/detail.dart';
import 'package:flutter/material.dart';

import '../model/user_model.dart';

class ListUser extends StatefulWidget {
  const ListUser({super.key});

  @override
  State<StatefulWidget> createState() => _ListUser();
}

// List
class _ListUser extends State<ListUser> {
  late List<User> users;

  @override
  void initState() {
    users = Repository.instance.userList;
    super.initState();
  }

  @override
  Widget build(BuildContext context) {
    return _buildList();
  }
  Widget _buildList(){
    return ListView.builder(
        key: const Key("listview"),
        itemCount: users.length,
        itemBuilder: (BuildContext context, int index) {
          if (index == 0) {
            return Row(
              children: [_buildHeaderFooter("Header", const Key("Header"))]
            );
          }
          if (index == users.length - 1) {
            return Row(
              children: [_buildHeaderFooter("Footer", const Key("Footer"))],
            );
          }
          return UserTile(
              key: Key("user_$index"), user: users.elementAt(index));
        }
    );
  }

  Widget _buildHeaderFooter(String text, Key? key) {
    return Padding(
      padding: const EdgeInsets.symmetric(vertical: 2, horizontal: 6),
      child: Padding(
        padding: EdgeInsets.only(left: 10, top: 10, bottom: 10),
        child: Container(
          alignment: AlignmentDirectional.centerStart,
          child: Text(
            text,
            style: TextStyle(fontSize: 30),
          ),
        ),
      ),
    );
  }
}

// List Item
class UserTile extends StatelessWidget {
  const UserTile({required Key key, required this.user}) : super(key: key);

  final User user;

  @override
  Widget build(BuildContext context) {
    return Padding(
      padding: const EdgeInsets.symmetric(vertical: 2, horizontal: 6),
      child: Card(
          child: ListTile(
            contentPadding:
                const EdgeInsets.symmetric(horizontal: 0, vertical: 10),
            title: Text(user.name),
            onTap: () {
              Navigator.of(context)
                  .push(_createRoute(DetailScreen(user: user)));
            },
            leading: CircleAvatar(
              radius: 35,
              backgroundImage: NetworkImage(user.photoUrl),
            ),
          ),
        ));
  }

  Route _createRoute(Widget param) {
    return PageRouteBuilder(
        pageBuilder: (context, animation, secondaryAnimation) => param,
        transitionsBuilder: (context, animation, secondaryAnimation, child) {
          const begin = Offset(1.0, 0.0);
          const end = Offset.zero;
          const curve = Curves.ease;
          var tweet =
              Tween(begin: begin, end: end).chain(CurveTween(curve: curve));
          return SlideTransition(
            position: animation.drive(tweet),
            child: child,
          );
        });
  }
}
