package com.alibasoglu.androidplayground.ui.dashboard

import android.animation.Animator
import android.animation.Animator.AnimatorListener
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.graphics.Color
import android.graphics.Matrix
import android.graphics.Path
import android.graphics.RectF
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.alibasoglu.androidplayground.R
import com.alibasoglu.androidplayground.databinding.FragmentDashboardBinding
import com.devs.vectorchildfinder.VectorChildFinder
import kotlin.math.abs

class DashboardFragment : Fragment() {

    private var _binding: FragmentDashboardBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val dashboardViewModel =
            ViewModelProvider(this).get(DashboardViewModel::class.java)

        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        binding.turkeyImageView.apply {
//            this.scaleX = 3f
//            this.scaleY = 3f
//            this.translationX = 30f
//            this.translationY = 40f
//            setOnClickListener {
//                it.translationX = 50f
//                it.translationY = 50f
//            }
//        }

        binding.turkeyImageView.setOnClickListener {
            val vectorChildFinder =
                VectorChildFinder(requireContext(), R.drawable.turkey, binding.turkeyImageView)

            val adanaPath = vectorChildFinder.findPathByName("adana")
            adanaPath.fillColor = Color.GREEN
            adanaPath.strokeWidth = 0f

            val ppp = Path()
            adanaPath.toPath(ppp)

            val pathBounds = RectF()
            ppp.computeBounds(pathBounds, true)
            Log.d("mylog", pathBounds.toString())

            // Scale calculation
            val imageWidth = binding.turkeyImageView.width
            val imageHeight = (binding.turkeyImageView.width * 334.56) / 792.6
            val imageView = binding.turkeyImageView

            val widthScale = imageWidth / pathBounds.width()
            val heightScale = (imageHeight / pathBounds.height()).toFloat()

// Choose the smaller scale factor to fit within the ImageView
            val scaleFactor = Math.min(widthScale, heightScale)

// Define scale animation
            val scaleXAnimation = ObjectAnimator.ofFloat(imageView, "scaleX", 1.0f, scaleFactor)
            val scaleYAnimation = ObjectAnimator.ofFloat(imageView, "scaleY", 1.0f, scaleFactor)

            val pathCenterX = pathBounds.centerX()
            val pathCenterY = pathBounds.centerY()

            Log.d("mylog", "imageWidth = $imageWidth  imageHeight = $imageHeight")

            val deviceCenterX = view.width / 2
            val deviceCenterY = view.height / 2

            Log.d("mylog", "deviceCenterX = $deviceCenterX  deviceCenterY = $deviceCenterY")

            val matrix = Matrix().apply {
                postScale(scaleFactor, scaleFactor)
                postTranslate(pathCenterX, pathCenterY)
            }
//
//            ppp.transform(matrix)
//
            //Define translation animation

            val m = imageView.imageMatrix

            val viewRect = RectF(0f, 0f, imageView.width.toFloat(), imageView.height.toFloat())
            m.setRectToRect(pathBounds, viewRect, Matrix.ScaleToFit.CENTER)


            imageView.imageMatrix = m



            val shiftX = abs(deviceCenterX - pathCenterX)
            val shiftY = abs(deviceCenterY - pathCenterY)
            Log.d("mylog", "shiftX = $shiftX  shiftY = $shiftY")
            Log.d("mylog", "pathCenterX = $pathCenterX  pathCenterY = $pathCenterY")

            val translateXAnimation =
                ObjectAnimator.ofFloat(
                    imageView,
                    "translationX",
                    deviceCenterX - pathCenterX
                )
            val translateYAnimation =
                ObjectAnimator.ofFloat(
                    imageView,
                    "translationY",
                    deviceCenterY - pathCenterY
                )

// Set the duration of the animation
            scaleXAnimation.duration = 2000
            scaleYAnimation.duration = 2000
            translateXAnimation.duration = 2000
            translateYAnimation.duration = 2000

// Create AnimatorSet
            val animatorSet = AnimatorSet()
            animatorSet.playTogether(
                scaleXAnimation,
                scaleYAnimation,
                translateXAnimation,
                translateYAnimation
            )

// Start the animation
            //animatorSet.start()
            animatorSet.addListener(object : AnimatorListener {
                override fun onAnimationStart(animation: Animator) {
                    TODO("Not yet implemented")
                }

                override fun onAnimationEnd(animation: Animator) {

                    binding.turkeyImageView.invalidate()
                }

                override fun onAnimationCancel(animation: Animator) {
                    TODO("Not yet implemented")
                }

                override fun onAnimationRepeat(animation: Animator) {
                    TODO("Not yet implemented")
                }

            })
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
